package com.webService.appointmentScheduling.DTO.doctor;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.util.Date;
import java.io.Serializable;

public class DoctorRequestDTO implements Serializable {
    private  static final long serialVersionUID = 1L;
    @Null
    private  Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
    private String tell;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(
            regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}",
            message = "CPF deve seguir o formato xxx.xxx.xxx-xx"
    )
    private String cpf;

    @NotBlank(message = "Especialização é obrigatória")
    private String specialization;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    public DoctorRequestDTO(){}

    public DoctorRequestDTO(Long id, String name, String tell, String cpf, String specialization, Date dataNascimento) {
        this.id = id;
        this.name = name;
        this.tell = tell;
        this.cpf = cpf;
        this.specialization = specialization;
        this.dataNascimento = dataNascimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
