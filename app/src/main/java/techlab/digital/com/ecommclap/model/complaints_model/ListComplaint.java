package techlab.digital.com.ecommclap.model.complaints_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListComplaint {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("guid")
    @Expose
    private Guid guid;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("modified_gmt")
    @Expose
    private String modifiedGmt;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("author")
    @Expose
    private Integer author;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("ticket-tag")
    @Expose
    private List<Object> ticketTag = null;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("ticket_priority")
    @Expose
    private String ticketPriority;
    @SerializedName("ticket_channel")
    @Expose
    private String ticketChannel;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("assignee")
    @Expose
    private Integer assignee;
    @SerializedName("secondary-assignee")
    @Expose
    private Integer secondaryAssignee;
    @SerializedName("tertiary-assignee")
    @Expose
    private Integer tertiaryAssignee;
    @SerializedName("customer-reply-count")
    @Expose
    private Integer customerReplyCount;
    @SerializedName("agent-reply-count")
    @Expose
    private Integer agentReplyCount;
    @SerializedName("total-reply-count")
    @Expose
    private Integer totalReplyCount;
    @SerializedName("time-calculated")
    @Expose
    private Integer timeCalculated;
    @SerializedName("time-adjustments")
    @Expose
    private Integer timeAdjustments;
    @SerializedName("time-final")
    @Expose
    private Integer timeFinal;
    @SerializedName("time-adjustments-type")
    @Expose
    private String timeAdjustmentsType;
    @SerializedName("time-notes")
    @Expose
    private String timeNotes;
    @SerializedName("_links")
    @Expose
    private Links links;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    public Guid getGuid() {
        return guid;
    }

    public void setGuid(Guid guid) {
        this.guid = guid;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModifiedGmt() {
        return modifiedGmt;
    }

    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Object> getTicketTag() {
        return ticketTag;
    }

    public void setTicketTag(List<Object> ticketTag) {
        this.ticketTag = ticketTag;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTicketPriority() {
        return ticketPriority;
    }

    public void setTicketPriority(String ticketPriority) {
        this.ticketPriority = ticketPriority;
    }

    public String getTicketChannel() {
        return ticketChannel;
    }

    public void setTicketChannel(String ticketChannel) {
        this.ticketChannel = ticketChannel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getAssignee() {
        return assignee;
    }

    public void setAssignee(Integer assignee) {
        this.assignee = assignee;
    }

    public Integer getSecondaryAssignee() {
        return secondaryAssignee;
    }

    public void setSecondaryAssignee(Integer secondaryAssignee) {
        this.secondaryAssignee = secondaryAssignee;
    }

    public Integer getTertiaryAssignee() {
        return tertiaryAssignee;
    }

    public void setTertiaryAssignee(Integer tertiaryAssignee) {
        this.tertiaryAssignee = tertiaryAssignee;
    }

    public Integer getCustomerReplyCount() {
        return customerReplyCount;
    }

    public void setCustomerReplyCount(Integer customerReplyCount) {
        this.customerReplyCount = customerReplyCount;
    }

    public Integer getAgentReplyCount() {
        return agentReplyCount;
    }

    public void setAgentReplyCount(Integer agentReplyCount) {
        this.agentReplyCount = agentReplyCount;
    }

    public Integer getTotalReplyCount() {
        return totalReplyCount;
    }

    public void setTotalReplyCount(Integer totalReplyCount) {
        this.totalReplyCount = totalReplyCount;
    }

    public Integer getTimeCalculated() {
        return timeCalculated;
    }

    public void setTimeCalculated(Integer timeCalculated) {
        this.timeCalculated = timeCalculated;
    }

    public Integer getTimeAdjustments() {
        return timeAdjustments;
    }

    public void setTimeAdjustments(Integer timeAdjustments) {
        this.timeAdjustments = timeAdjustments;
    }

    public Integer getTimeFinal() {
        return timeFinal;
    }

    public void setTimeFinal(Integer timeFinal) {
        this.timeFinal = timeFinal;
    }

    public String getTimeAdjustmentsType() {
        return timeAdjustmentsType;
    }

    public void setTimeAdjustmentsType(String timeAdjustmentsType) {
        this.timeAdjustmentsType = timeAdjustmentsType;
    }

    public String getTimeNotes() {
        return timeNotes;
    }

    public void setTimeNotes(String timeNotes) {
        this.timeNotes = timeNotes;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
