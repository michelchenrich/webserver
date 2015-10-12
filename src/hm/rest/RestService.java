package hm.rest;

public interface RestService {
    public abstract void run(Request request, Response response) throws Exception;
}