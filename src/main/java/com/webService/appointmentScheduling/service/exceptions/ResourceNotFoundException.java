package com.webService.appointmentScheduling.service.exceptions;

public class ResourceNotFoundException extends  RuntimeException{
    private  static  final  long serialVersionUID = 1L;
    public ResourceNotFoundException(Object id){
        super("Resource not found. ID"+id);
    }
}
