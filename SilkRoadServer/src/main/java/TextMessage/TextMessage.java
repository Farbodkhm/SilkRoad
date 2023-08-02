package TextMessage;

import java.util.Date;

public class TextMessage
{

    private int textID;
    private int senderID;
    private int receiverID;
    private String content;
    private Date date;
    private String senderUsername;

    public TextMessage(int textID, int senderID, int receiverID, String content, Date date) {
        this.textID = textID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.content = content;
        this.date = date;
    }

    public TextMessage(int textID, int senderID, int receiverID, String content) {
        this.textID = textID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.content = content;
    }

    public TextMessage(int senderID, int receiverID, String content) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.content = content;
    }

    public TextMessage(int textID, String content, Date date, String senderUsername) {
        this.textID = textID;
        this.content = content;
        this.date = date;
        this.senderUsername = senderUsername;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public int getTextID() {
        return textID;
    }
}
