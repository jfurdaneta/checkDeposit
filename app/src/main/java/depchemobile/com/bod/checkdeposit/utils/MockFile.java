package depchemobile.com.bod.checkdeposit.utils;

import depchemobile.com.bod.checkdeposit.web.WebConstants;


import java.util.ArrayList;

/**
 * Created by Rony R. Diaz M.  on 19/05/2015.
 */
public class MockFile {

    public static String getMockStatusUser() {

        return "R";
    }

    public static String getMockTimeOut() {

        return "3600";
    }
/*
    public static Cliente getMockCliente() {

        Cliente cliente = new Cliente();
        //cliente.setAvatar();
        cliente.setFechaAfiliacion(Utiles.formatStringToDate("Fri Oct 24 15:13:13 VET 2014"));
        cliente.setStatus("R");
        cliente.setUserId(123);
        cliente.setUsername("rodolforojas");
        cliente.setNombre("Rodolfo Rojas");
        cliente.setTipoIdentificacion("V");
        cliente.setUltimoLogin(Utiles.formatStringToDate("Fri Oct 24 15:13:13 VET 2014"));
        cliente.setPartyId("5424");
        return cliente;


    }

    */


    public static ArrayList getMockAsociados() {
        ArrayList asociados = new ArrayList();
/*
        Asociado asociado = new Asociado();
        asociado.setNombre("Juan Perez");
        asociado.setEmail("juan_perez@gmail.com");
        asociado.setTipoIdentificacion("V");
        asociado.setAlias("cuhcho");
        asociado.setTelefono("04123748292");
        asociado.setIdentificacion("152839485");
        asociado.setUserId(133);
        asociado.setStatus("A");

        asociado.setListaProductos(getMockListaProductos());

        asociados.add(asociado);

        asociado = new Asociado();
        asociado.setNombre("Luis Mata");
        asociado.setEmail("luis@gmail.com");
        asociado.setTipoIdentificacion("V");
        asociado.setAlias("luis");
        asociado.setTelefono("04123742222");
        asociado.setIdentificacion("152839468");
        asociado.setUserId(123);
        asociado.setStatus("A");

        asociado.setListaProductos(getMockListaProductos2());

        asociados.add(asociado);

        asociado = new Asociado();
        asociado.setNombre("Juan Valdez");
        asociado.setEmail("juaerez@gmail.com");
        asociado.setTipoIdentificacion("V");
        asociado.setAlias("valdez");
        asociado.setTelefono("04123748200");
        asociado.setIdentificacion("152839219");
        asociado.setUserId(113);
        asociado.setStatus("A");

        asociado.setListaProductos(getMockListaProductos3());

        asociados.add(asociado);
        */

        return asociados;
    }





    public static ArrayList getMockListaPreguntasDesafio() {

        ArrayList preguntas = new ArrayList();



        return preguntas;
    }



}
