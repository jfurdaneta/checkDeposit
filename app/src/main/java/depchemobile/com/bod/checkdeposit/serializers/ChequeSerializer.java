package depchemobile.com.bod.checkdeposit.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import depchemobile.com.bod.checkdeposit.data.ChequeContract;
import depchemobile.com.bod.checkdeposit.entidades.Cheque;
import depchemobile.com.bod.checkdeposit.utils.Utils;

/**
 * Created by rony_2 on 25/8/2016.
 */
public class ChequeSerializer implements JsonSerializer <Cheque> {


    @Override
    public JsonElement serialize(Cheque cheque, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();

        object.addProperty(ChequeContract.ChequeEntry.FECHA_PROCESO, Utils.FormateadorFechatoServicio(cheque.getFechaProceso()));
        object.addProperty(ChequeContract.ChequeEntry.MONTO,cheque.getMonto());
        object.addProperty(ChequeContract.ChequeEntry.MISMO_BANCO,cheque.isMismoBanco());
        object.addProperty(ChequeContract.ChequeEntry.NUMERO_CUENTA,cheque.getNumCuenta());
        String imagenFrente =    Utils.uri_to_imageString(cheque.getImgChequeFront());
        String imagenTrasera =   Utils.uri_to_imageString(cheque.getImgChequeBack());

        object.addProperty(ChequeContract.ChequeEntry.IMAGEN_CHEQUE_FRENTE,imagenFrente);
        object.addProperty(ChequeContract.ChequeEntry.NOMBRE_BANCO,"BOD");
        object.addProperty(ChequeContract.ChequeEntry.IMAGEN_CHEQUE_TRASERA,imagenTrasera);
        object.addProperty(ChequeContract.ChequeEntry.NUMERO_LOTE,cheque.getNumLote());




        return object;
    }
}
