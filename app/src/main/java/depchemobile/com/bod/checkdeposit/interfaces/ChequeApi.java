package depchemobile.com.bod.checkdeposit.interfaces;

import com.google.gson.JsonObject;

import java.util.HashMap;

import depchemobile.com.bod.checkdeposit.entidades.Cheque;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by rony_2 on 25/8/2016.
 */
public interface ChequeApi {

    @POST("cheques")
    Call<JsonObject> crearCheque(@Body Cheque cheque );

}
