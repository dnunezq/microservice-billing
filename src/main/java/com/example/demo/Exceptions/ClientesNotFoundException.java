/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.Exceptions;

/**
 *
 * @author Sonia Sanabria
 */
public class ClientesNotFoundException extends RuntimeException {
    public ClientesNotFoundException(String message) {
        super(message);
}
}
