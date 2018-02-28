package dth.com.yun.http.rx;

/**
 * Created by dth
 * Des:
 * Date: 16/5/17.
 */
public class RxBusBaseMessage {
    private int code;
    private Object object;

    public RxBusBaseMessage(int code, Object object){
        this.code=code;
        this.object=object;
    }
    public RxBusBaseMessage(){}

    public int getCode() {
        return code;
    }

    public Object getObject() {
        return object;
    }
}
