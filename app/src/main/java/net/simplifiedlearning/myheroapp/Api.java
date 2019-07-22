package net.simplifiedlearning.myheroapp;

/**
 * Created by Belal on 9/9/2017.
 */

public class Api {

    private static final String ROOT_URL = "http://192.168.0.106/HeroApi/v1/Api.php?apicall=";

    public static final String URL_GET_TODAYSWORKTIME = ROOT_URL + "gettodaysworktime";
    public static final String URL_UPDATE_TODAYSWORKTIME = ROOT_URL + "updatetodaysworktime";
    public static final String URL_GET_WORKERBOOLEAN = ROOT_URL + "getworkersboolean&login_token=";
    public static final String URL_CHANGE_BOOLEANTOTRUE = ROOT_URL + "changebooleantotrue";
    public static final String URL_CHANGE_BOOLEANTOFALSE = ROOT_URL + "changebooleantofalse";
    public static final String URL_CHANGE_LASTTIMESTARTED = ROOT_URL + "changelasttimestarted";
    public static final String URL_CHANGE_LASTTIMEENDED = ROOT_URL + "changelasttimeended";
    public static final String URL_COPY_WORKER = ROOT_URL + "copyworker";

}
