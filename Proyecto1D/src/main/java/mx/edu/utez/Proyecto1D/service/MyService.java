package mx.edu.utez.Proyecto1D.service;

import mx.edu.utez.Proyecto1D.controller.dto.RequestCalculadoraDTO;
import mx.edu.utez.Proyecto1D.controller.dto.ResponseCalculadoraDTO;
import mx.edu.utez.Proyecto1D.exception.customExceptions.CustomBadRequestException;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    public ResponseCalculadoraDTO calculadora(RequestCalculadoraDTO data) {
        ResponseCalculadoraDTO respuesta = new ResponseCalculadoraDTO();

        if(
                !data.getOperacion().equals("SUMA")
                && !data.getOperacion().equals("RETA")
                && !data.getOperacion().equals("MULTIPLICACION")

        ) {
            throw new CustomBadRequestException("La operaion solicitada no es valida, debe ser SUMA, RESTA Y MULTIPLICACION" +
                    "");
        }

        switch (data.getOperacion()) {
            case "SUMA":
                respuesta.setResultado(
                        data.getNum1() + data.getNum2()
                );
                break;

            case "RESTA":
                respuesta.setResultado(
                        data.getNum1() - data.getNum2()
                );
                break;

            case "MULTIPLICACION":
                respuesta.setResultado(
                        data.getNum1() * data.getNum2()
                );
                break;
        }

        respuesta.setOpeacionRealzada(data.getOperacion());

        return respuesta;

    }
}
