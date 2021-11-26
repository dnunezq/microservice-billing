
package com.example.demo.Controllers;

/**
 * @author Sonia Sanabria
 */
import com.example.demo.Exceptions.ClientesNotFoundException;
import com.example.demo.Models.Clientes;
import com.example.demo.Repositories.RepositorioClientes;
import org.springframework.web.bind.annotation.*;


@RestController
public class ClientesController {
private final RepositorioClientes RepositorioClientes;

public ClientesController(RepositorioClientes RepositorioClientes) {
this.RepositorioClientes = RepositorioClientes;
}
@GetMapping("/clientes/{factura}")
Clientes getClientes(@PathVariable String factura){
return RepositorioClientes.findById(factura)
        .orElseThrow(() -> new ClientesNotFoundException("No se encontro la factura"));

}
@PostMapping("/clientes")
Clientes newClientes(@RequestBody Clientes clientes){
return RepositorioClientes.save(clientes);
}
}