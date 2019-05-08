package techlab.digital.com.ecommclap.model.CreateTicketModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Links {

    @SerializedName("self")
    @Expose
    private List<Self> self = null;
    @SerializedName("collection")
    @Expose
    private List<Collection> collection = null;
    @SerializedName("replies")
    @Expose
    private List<Reply> replies = null;
    @SerializedName("history")
    @Expose
    private List<History> history = null;
    @SerializedName("author")
    @Expose
    private List<Author> author = null;
    @SerializedName("assignee")
    @Expose
    private List<Assignee> assignee = null;
    @SerializedName("about")
    @Expose
    private List<About> about = null;
    @SerializedName("wp:attachment")
    @Expose
    private List<WpAttachment> wpAttachment = null;
    @SerializedName("wp:term")
    @Expose
    private List<WpTerm> wpTerm = null;
    @SerializedName("wp:action-publish")
    @Expose
    private List<WpActionPublish> wpActionPublish = null;
    @SerializedName("wp:action-create-ticket-tag")
    @Expose
    private List<WpActionCreateTicketTag> wpActionCreateTicketTag = null;
    @SerializedName("wp:action-assign-ticket-tag")
    @Expose
    private List<WpActionAssignTicketTag> wpActionAssignTicketTag = null;
    @SerializedName("wp:action-assign-product")
    @Expose
    private List<WpActionAssignProduct> wpActionAssignProduct = null;
    @SerializedName("wp:action-assign-ticket_priority")
    @Expose
    private List<WpActionAssignTicketPriority> wpActionAssignTicketPriority = null;
    @SerializedName("wp:action-assign-ticket_channel")
    @Expose
    private List<WpActionAssignTicketChannel> wpActionAssignTicketChannel = null;
    @SerializedName("curies")
    @Expose
    private List<Cury> curies = null;

    public List<Self> getSelf() {
        return self;
    }

    public void setSelf(List<Self> self) {
        this.self = self;
    }

    public List<Collection> getCollection() {
        return collection;
    }

    public void setCollection(List<Collection> collection) {
        this.collection = collection;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    public List<Assignee> getAssignee() {
        return assignee;
    }

    public void setAssignee(List<Assignee> assignee) {
        this.assignee = assignee;
    }

    public List<About> getAbout() {
        return about;
    }

    public void setAbout(List<About> about) {
        this.about = about;
    }

    public List<WpAttachment> getWpAttachment() {
        return wpAttachment;
    }

    public void setWpAttachment(List<WpAttachment> wpAttachment) {
        this.wpAttachment = wpAttachment;
    }

    public List<WpTerm> getWpTerm() {
        return wpTerm;
    }

    public void setWpTerm(List<WpTerm> wpTerm) {
        this.wpTerm = wpTerm;
    }

    public List<WpActionPublish> getWpActionPublish() {
        return wpActionPublish;
    }

    public void setWpActionPublish(List<WpActionPublish> wpActionPublish) {
        this.wpActionPublish = wpActionPublish;
    }

    public List<WpActionCreateTicketTag> getWpActionCreateTicketTag() {
        return wpActionCreateTicketTag;
    }

    public void setWpActionCreateTicketTag(List<WpActionCreateTicketTag> wpActionCreateTicketTag) {
        this.wpActionCreateTicketTag = wpActionCreateTicketTag;
    }

    public List<WpActionAssignTicketTag> getWpActionAssignTicketTag() {
        return wpActionAssignTicketTag;
    }

    public void setWpActionAssignTicketTag(List<WpActionAssignTicketTag> wpActionAssignTicketTag) {
        this.wpActionAssignTicketTag = wpActionAssignTicketTag;
    }

    public List<WpActionAssignProduct> getWpActionAssignProduct() {
        return wpActionAssignProduct;
    }

    public void setWpActionAssignProduct(List<WpActionAssignProduct> wpActionAssignProduct) {
        this.wpActionAssignProduct = wpActionAssignProduct;
    }

    public List<WpActionAssignTicketPriority> getWpActionAssignTicketPriority() {
        return wpActionAssignTicketPriority;
    }

    public void setWpActionAssignTicketPriority(List<WpActionAssignTicketPriority> wpActionAssignTicketPriority) {
        this.wpActionAssignTicketPriority = wpActionAssignTicketPriority;
    }

    public List<WpActionAssignTicketChannel> getWpActionAssignTicketChannel() {
        return wpActionAssignTicketChannel;
    }

    public void setWpActionAssignTicketChannel(List<WpActionAssignTicketChannel> wpActionAssignTicketChannel) {
        this.wpActionAssignTicketChannel = wpActionAssignTicketChannel;
    }

    public List<Cury> getCuries() {
        return curies;
    }

    public void setCuries(List<Cury> curies) {
        this.curies = curies;
    }

}
