package Entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clanner on 2017/1/20.
 */
@Component
@Scope("request")
public class Response<T> {

    private Integer code;
    private String message;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    private List<T> dataList;

    public Response error(String message) {
        GeneralResponseHolder.generalResponse.setCode(40000);
        GeneralResponseHolder.generalResponse.setMessage(message);
        return GeneralResponseHolder.generalResponse;
    }

    public Response success(String message) {
        GeneralResponseHolder.generalResponse.setCode(20000);
        GeneralResponseHolder.generalResponse.setMessage(message);
        return GeneralResponseHolder.generalResponse;
    }

    public Response successWithData(String message, T data) {
        DataResponseHolder.dataResponse.setCode(20000);
        DataResponseHolder.dataResponse.setMessage(message);
        DataResponseHolder.dataResponse.setData(data);
        return DataResponseHolder.dataResponse;
    }

    public Response successWithDataList(String message, List<T> dataList) {
        DataListResponseHolder.dataListResponse.setCode(20000);
        DataListResponseHolder.dataListResponse.setMessage(message);
        DataListResponseHolder.dataListResponse.setDataList(dataList);
        return DataListResponseHolder.dataListResponse;
    }

    public Response successWithAll(String message, T data, List<T> dataList) {
        ResponseHolder.response.setCode(20000);
        ResponseHolder.response.setMessage(message);
        ResponseHolder.response.setData(data);
        ResponseHolder.response.setDataList(dataList);
        return ResponseHolder.response;
    }


    private static class GeneralResponseHolder{
        private static final Response generalResponse = new Response();
    }

    private static class DataResponseHolder{
        private static final Response dataResponse = new Response();
    }

    private static class DataListResponseHolder{
        private static final Response dataListResponse = new Response();
    }

    private static class ResponseHolder{
        private static final Response response = new Response();
    }

//    private Response() {
//    }
//
//    public Response error(String message) {
//        Response response = Single.response;
//        response.setCode(40000);
//        response.setMessage(message);
//        return response;
//    }
//
//    @Component
//    public static class Builder<T> {
//        private Integer code;
//        private String message;
//        private T data;
//        private List<T> dataList = new ArrayList<T>();
//
//        public Builder setCode(Integer code) {
//            this.code = code;
//            return this;
//        }
//
//        public Builder setMessage(String message) {
//            this.message = message;
//            return this;
//        }
//
//        public Builder setData(T data) {
//            this.data = data;
//            return this;
//        }
//
//        public Builder setDataList(List<T> dataList) {
//            this.dataList = dataList;
//            return this;
//        }
//
//        public Response build() {
//            Single.response.code = code;
//            Single.response.message = message;
//            Single.response.data = data;
//            Single.response.dataList = dataList;
//            code = null;
//            message = null;
//            data = null;
//            dataList = null;
//            return Single.response;
//        }
//    }
//
//    private static final class Single {
//        private static final Response response = new Response();
//    }
}

