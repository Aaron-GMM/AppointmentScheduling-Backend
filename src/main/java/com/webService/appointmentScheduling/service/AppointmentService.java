package com.webService.appointmentScheduling.service;
import com.webService.appointmentScheduling.DTO.appointment.AppointmentRequestDTO;
import com.webService.appointmentScheduling.DTO.appointment.AppointmentResponseDTO;
import com.webService.appointmentScheduling.entities.Appointment;
import com.webService.appointmentScheduling.entities.Doctor;
import com.webService.appointmentScheduling.entities.Patients;
import com.webService.appointmentScheduling.repositories.appointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private appointmentRepository repository;

    @Autowired patientsService patientsService;
    @Autowired doctorService doctorService;

    private  final LocalTime startTime = LocalTime.of(8,0);
    private  final LocalTime endTime = LocalTime.of(14,0);

    public Appointment scheduleAppointment(AppointmentRequestDTO requestDTO){
        if (requestDTO.getHora().isBefore(startTime)|| requestDTO.getHora().isAfter(endTime)){
            throw  new IllegalArgumentException("Horario fora do intervalo permitido (8:00-14:00).");
        }
        if (!isDoctorAvailable(requestDTO.getDoctorId(),requestDTO.getDate(),requestDTO.getHora())){
            throw  new IllegalArgumentException("o médico já tem uma consulta agendada nesse horário. ");
        }
        if(!isPatientAvailable(requestDTO.getPatientId(),requestDTO.getDate(),requestDTO.getHora())){
            throw  new IllegalArgumentException("o paciente já tem uma consulta nesse horário.");
        }
        Appointment appointment = fromDTO(requestDTO);
        return  repository.save(appointment);
    }

    private boolean isDoctorAvailable(Long id, LocalDate date, LocalTime time){
        return  repository.findByDoctorAndDateAndTime(id,date,time).isEmpty();
    }

    private  boolean isPatientAvailable(Long id, LocalDate date, LocalTime time){
        return repository.findByPatientAndDateAndTime(id,date,time).isEmpty();
    }
    public Appointment fromDTO(AppointmentRequestDTO dto){
        Patients patient = patientsService.findById(dto.getPatientId());
        Doctor doctor = doctorService.findById(dto.getDoctorId());
        return  new Appointment(null, dto.getHora(),dto.getDate(),dto.getDescription(),patient,doctor);
    }
    public AppointmentResponseDTO toDTO(Appointment appointment){

        return  new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getHora(),
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
