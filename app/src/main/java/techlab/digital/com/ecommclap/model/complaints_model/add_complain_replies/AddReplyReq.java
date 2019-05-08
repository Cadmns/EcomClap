package techlab.digital.com.ecommclap.model.complaints_model.add_complain_replies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddReplyReq {
    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("author")
    @Expose
    private String author;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
