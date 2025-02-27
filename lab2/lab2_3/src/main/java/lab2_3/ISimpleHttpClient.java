package lab2_3;

import java.io.IOException;

interface ISimpleHttpClient {
    String doHttpGet(String url) throws IOException;
}
