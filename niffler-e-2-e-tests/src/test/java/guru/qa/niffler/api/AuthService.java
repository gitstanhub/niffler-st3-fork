package guru.qa.niffler.api;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {


    // init state - empty cookies
    // codeChallenge - tKHJPrGaQUc2ymhnkcn-ZZ2Y5eTEiKXoO7ZJvaz7G78
    // codeVerified - CLGjQ7Qhqw6bT08kldl7njv8qNUHwOT6hKNcPq6RRwg
    // id_token - ...
    // empty local storage

    @GET("/oauth2/authorize")
    Call<Void> authorize(

    );


    //first_request
    // http://127.0.0.1:9000/oauth2/authorize
    // ?response_type=code
    // &client_id=client
    // &scope=openid
    // &redirect_uri=http://127.0.0.1:3000/authorized
    // &code_challenge=9a21WLDvclvxiQ3inNagUx70gAGuUQztZEiCXFWWDNc
    // &code_challenge_method=S256
    // JSESSIONID=CBA647BB1130181AD4035A5E6AA1FEAB; Path=/
    // XSRF-TOKEN=2caf0c54-8d62-41bc-827b-6c955c85ffbe; Path=/

    //


    @POST("/login")
    @FormUrlEncoded
    Call<Void> login(

    );


    // second request
    // http://127.0.0.1:9000/login
    // body:
    // _csrf: 8230a3b5-f675-4f6d-95b9-bacf6b3540bf
    // username: stan_one
    // password: 123456
    //
    // headers:
    // request cookies JSESSIONID=81D54286185E04935B5FD2F20698AD04; XSRF-TOKEN=8230a3b5-f675-4f6d-95b9-bacf6b3540bf
    // Location:
    // http://127.0.0.1:9000/oauth2/authorize
    // ?response_type=code
    // &client_id=client
    // &scope=openid
    // &redirect_uri=http://127.0.0.1:3000/authorized
    // &code_challenge=DHlv5xALhILksO2yi2k8hjVlrysWUqyKYUiwnGmanWw
    // &code_challenge_method=S256&continue
    // Set-Cookie:
    //JSESSIONID=1EE30899AB84DDE420590363AF70A6CA; Path=/
    //
    //Set-Cookie:
    //XSRF-TOKEN=; Path=/; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT

    // second extra requrest
    // redirect request: http://127.0.0.1:9000/oauth2/authorize
    // ?response_type=code&client_id=client&scope=openid
    // &redirect_uri=http://127.0.0.1:3000/authorized
    // &code_challenge=DHlv5xALhILksO2yi2k8hjVlrysWUqyKYUiwnGmanWw
    // &code_challenge_method=S256&continue
    // req headers: Cookie:
    //JSESSIONID=1EE30899AB84DDE420590363AF70A6CA

    // another extra request
    // Request URL:
    // http://127.0.0.1:3000/authorized
    // ?code=kx6Jy_w1FXiawpBPxvhkOar3lvt88j6b1yniy1lMC2DeVYv7kCt1degSGv68cXdKB16ewOHdRvkPGa4XrGqsU5MOtMJRQaaQ620w4fqQie5ezEg9I8bAH3lpQBc57KOI
    // request cookie JSESSIONID=1EE30899AB84DDE420590363AF70A6CA
    // code 200



    @POST("oauth2/token")
    Call<JsonNode> token(

    );

    // third request
    // Authorization:
    // Basic Y2xpZW50OnNlY3JldA==
    // result: 200
    // {
    //    "access_token": "eyJraWQiOiI4MjlmNTIwMy1iOGZkLTRlZTctOTYzNy00Njk3Zjk1OGM5YjYiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzdGFuX29uZSIsImF1ZCI6ImNsaWVudCIsIm5iZiI6MTcwNjg5OTIxMiwic2NvcGUiOlsib3BlbmlkIl0sImlzcyI6Imh0dHA6Ly8xMjcuMC4wLjE6OTAwMCIsImV4cCI6MTcwNjkzNTIxMiwiaWF0IjoxNzA2ODk5MjEyfQ.KAvQI18-QLhIv_0Ibs1FbcZIhJlG10sZm56f7vhFuP4R2fgvc8V41jfHfQIKua6NYgpGvaHfkOvWOwkL4zNMxiC7N7CCF-ojaAYfnhmXoHFdtimW4qGWs0c_z_4B2qMbs3wkpMeJqYd3rgcGQdSMnI0geLBL7g4nuVpzU_bXjnq8OHP3Ir0uVlXbAN3VVJ6cUyZS-QhXGEFosUTHwcLbUpF1LtCv2KqrJEqXeMAflgLOGp2o1i0k2nptE3KlOROgL-h3v81TYWzgaabfSJe0HNqX4Terd3TVw-OLgC6WvmY-Nf9QlQEmrKSphm0HbAHvMQ2Gu-KeuLsiMlwGiPJ-VQ",
    //    "refresh_token": "e6wny3suZy0-bUi1P3dFy-C-lN7eooxe_d0iGM6dzG5THXScHZoNkh79XTTa-t9TZiZPBLZQNqGIZCoCCFCEq5Ekc_0tbbVl0H5EsdSl9VOHqcgoIXhDxz-kzXYwFmXw",
    //    "scope": "openid",
    //    "id_token": "eyJraWQiOiI4MjlmNTIwMy1iOGZkLTRlZTctOTYzNy00Njk3Zjk1OGM5YjYiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzdGFuX29uZSIsImF1ZCI6ImNsaWVudCIsImF6cCI6ImNsaWVudCIsImF1dGhfdGltZSI6MTcwNjg5OTIxMSwiaXNzIjoiaHR0cDovLzEyNy4wLjAuMTo5MDAwIiwiZXhwIjoxNzA2OTAxMDEyLCJpYXQiOjE3MDY4OTkyMTIsInNpZCI6InRBRWZwOEhyNkJGYnBZWlhBcU1wQWVYcVpvT25MdmxFUXhfMjlsMmszSTAifQ.wMDVkJOKTG1_NoDzeUwsH2hAhCI1N8gmOvxHN8NhMXoSHoSu-FY35NAR5tlwf5-RAIqlBKH0XTXurA6mkHI1mC9AFggJQDBwdUeDcCgEyFZhAuxhmtsBRsj60tNR9BOItvioOGTmI7kc97i5_wAQsKKt-lH2mSnkh-WyMleEkyt-m5XBK5mi8rZwkbAv3JlwPcdnZkWlkPBFN4QqWp7ojeCr4YdqbthWjXcIFaAqtSb-Pz2D3UCGy-a14uS8wgBI3dInWoEsG2BfmhYIpvRhDTuE_42tbR6kDo90_-XPF4UXEZqwrmN28GgvUYF-QDGNnsKWkBzT_QgScsylcHfvVw",
    //    "token_type": "Bearer",
    //    "expires_in": 35999
    //}

    // session storage:
    // id_token : from the json


}
