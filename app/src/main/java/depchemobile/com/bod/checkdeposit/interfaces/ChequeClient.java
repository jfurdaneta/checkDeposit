package depchemobile.com.bod.checkdeposit.interfaces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import depchemobile.com.bod.checkdeposit.entidades.Cheque;
import depchemobile.com.bod.checkdeposit.serializers.ChequeSerializer;
import depchemobile.com.bod.checkdeposit.web.WebConstants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rony_2 on 25/8/2016.
 */
public class ChequeClient {
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {

            Gson gson = new GsonBuilder()

                    .registerTypeAdapter(Cheque.class, new ChequeSerializer()).setPrettyPrinting()
                    .create();


            retrofit = new Retrofit.Builder()
                    .baseUrl(WebConstants.UPLOAD_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
