package com.webService.appointmentScheduling.service;
import com.webService.appointmentScheduling.DTO.appointment.AppointmentRequestDTO;
import com.webService.appointmentScheduling.DTO.appointment.AppointmentResponseDTO;
import com.webService.appointmentScheduling.DTO.patients.PatientsResponseDTO;
import com.webService.appointmentScheduling.entities.Appointment;
import com.webService.appointmentScheduling.entities.Doctor;
import com.webService.appointmentScheduling.entities.Patients;
import com.webService.appointmentScheduling.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repository;

    @Autowired
    PatientsService patientsService;
    @Autowired
    DoctorService doctorService;

    private  final LocalTime startTime = LocalTime.of(8,0);
    private  final LocalTime endTime = LocalTime.of(14,0);

    public Appointment scheduleAppointment(AppointmentRequestDTO requestDTO){
        if (requestDTO.getTime().isBefore(startTime)|| requestDTO.getTime().isAfter(endTime)){
            throw  new IllegalArgumentException("timer fora do intervalo permitido (8:00-14:00).");
        }
        if (!isDoctorAvailable(requestDTO.getDoctorId(),requestDTO.getDate(),requestDTO.getTime())){
            throw  new IllegalArgumentException("o médico já tem uma consulta agendada nesse horário. ");
        }
        if(!isPatientAvailable(requestDTO.getPatientId(),requestDTO.getDate(),requestDTO.getTime())){
            throw  new IllegalArgumentException("o paciente já tem uma consulta nesse horário.");
        }
        Appointment appointment = fromDTO(requestDTO);
        return  repository.save(appointment);
    }

    private boolean isDoctorAvailable(Long doctorId, LocalDate date, LocalTime time){
        Doctor doctor = doctorService.findByIdEntity(doctorId);
        return  repository.findByDoctorAndDateAndTime(doctor.getId(),date,time).isEmpty();
    }

    private  boolean isPatientAvailable(Long patientId, LocalDate date, LocalTime time){
        Patients patient = patientsService.findByIdEntity(patientId);
        return repository.findByPatientAndDateAndTime(patient.getId(),date,time).isEmpty();
    }
    public Appointment fromDTO(AppointmentRequestDTO dto){
        Patients patient = patientsService.findByIdEntity(dto.getPatientId());
        Doctor doctor = doctorService.findByIdEntity(dto.getDoctorId());

        return  new Appointment(null, dto.getTime(),dto.getDate(),dto.getDescription(),patient,doctor);
    }
    public AppointmentResponseDTO toDTO(Appointment appointment){

        return  new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getTime(),
                appointment.getDate(),
                appointment.getDescription(),
                appointment.getPatient().getName(),
                appointment.getDoctor().getName()
        );
    }

    public List<AppointmentResponseDTO> listAppointmentsByTime(LocalDate date, LocalTime time){
        List<Appointment> appointments = repository.findByDateAndTime(date,time);
        return  appointments.stream()
                .map(this::toDTO)
                .toList();
    }
    public void cancelAppointment(Long appointmentId) {
        Optional<Appointment> appointmentOpt = repository.findById(appointmentId);
        if (appointmentOpt.isPresent()) {
            repository.delete(appointmentOpt.get());
        } else {
            throw new IllegalArgumentException("Consulta não encontrada.");
        }
    }
}
