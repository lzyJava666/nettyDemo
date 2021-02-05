package netty.protocoltcp;
//协议内容
public class MessageProtocol {
    //长度
    private int len;
    //消息内容
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
