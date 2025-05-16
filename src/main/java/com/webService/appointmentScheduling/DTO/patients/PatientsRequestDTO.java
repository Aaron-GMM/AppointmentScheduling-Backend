package com.webService.appointmentScheduling.DTO.patients;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

public class PatientsRequestDTO  implements Serializable {
    private  static  final  long serialVersionUID = 1L;

    @Null
    private  Long id;
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(
            regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}",
            message = "CPF deve seguir o formato xxx.xxx.xxx-xx"
    )
    private String cpf;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
    private String tell;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    public PatientsRequestDTO(Long id, String name, String cpf, String tell, Date dataNascimento) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.tell = tell;
        this.dataNascimento = dataNascimento;
    }

    public PatientsRequestDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


}
