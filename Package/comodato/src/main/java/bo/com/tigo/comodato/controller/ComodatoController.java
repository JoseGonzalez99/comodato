package bo.com.tigo.comodato.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


import bo.com.tigo.comodato.DTO.dto.WsComodatoValidRequest;
import bo.com.tigo.comodato.service.ComodatoService;


@RestController
@RequestMapping("/v1/tigo/bo")
public class ComodatoController {
    private final ComodatoService comodatoService;
    /*
     * Se injecta el servicio de crearComprobante
     */
    @Autowired
    public ComodatoController(ComodatoService comodatoService) {
        this.comodatoService = comodatoService;
    }

    @PostMapping("/comodato")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> associateComodato(@Valid @RequestBody WsComodatoValidRequest bodyrequest, BindingResult resulVal, HttpServletRequest httpRequest) throws Exception {
        /*
         * validaciones del request
         */
        if (resulVal.hasErrors()){
            Map<String,String> errores = new HashMap<>();
            errores.put("codigo","400");
            //errores.put("codeType","Bad Request");
            resulVal.getFieldErrors().forEach(err ->{
                errores.put("mensaje","La propiedad -> " + err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }

        return comodatoService.associateComodato(bodyrequest, httpRequest);
    }

}
