package techlab.digital.com.ecommclap.model.complaints_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meta {
    @SerializedName("_wpas_status")
    @Expose
    private List<String> wpasStatus = null;
    @SerializedName("_wpas_assignee")
    @Expose
    private List<String> wpasAssignee = null;
    @SerializedName("_order_id")
    @Expose
    private List<String> orderId = null;
    @SerializedName("_edit_lock")
    @Expose
    private List<String> editLock = null;
    @SerializedName("_edit_last")
    @Expose
    private List<String> editLast = null;
    @SerializedName("_wpas_ttl_replies_by_customer")
    @Expose
    private List<String> wpasTtlRepliesByCustomer = null;
    @SerializedName("_wpas_ttl_replies_by_agent")
    @Expose
    private List<String> wpasTtlRepliesByAgent = null;
    @SerializedName("_wpas_ttl_replies")
    @Expose
    private List<String> wpasTtlReplies = null;
    @SerializedName("_wpas_last_reply_date")
    @Expose
    private List<String> wpasLastReplyDate = null;
    @SerializedName("_wpas_last_reply_date_gmt")
    @Expose
    private List<String> wpasLastReplyDateGmt = null;
    @SerializedName("_wpas_is_waiting_client_reply")
    @Expose
    private List<String> wpasIsWaitingClientReply = null;
    @SerializedName("auto_delete_attachments")
    @Expose
    private List<String> autoDeleteAttachments = null;
    @SerializedName("auto_delete_attachments_type")
    @Expose
    private List<String> autoDeleteAttachmentsType = null;

    public List<String> getWpasStatus() {
        return wpasStatus;
    }

    public void setWpasStatus(List<String> wpasStatus) {
        this.wpasStatus = wpasStatus;
    }

    public List<String> getWpasAssignee() {
        return wpasAssignee;
    }

    public void setWpasAssignee(List<String> wpasAssignee) {
        this.wpasAssignee = wpasAssignee;
    }

    public List<String> getOrderId() {
        return orderId;
    }

    public void setOrderId(List<String> orderId) {
        this.orderId = orderId;
    }

    public List<String> getEditLock() {
        return editLock;
    }

    public void setEditLock(List<String> editLock) {
        this.editLock = editLock;
    }

    public List<String> getEditLast() {
        return editLast;
    }

    public void setEditLast(List<String> editLast) {
        this.editLast = editLast;
    }

    public List<String> getWpasTtlRepliesByCustomer() {
        return wpasTtlRepliesByCustomer;
    }

    public void setWpasTtlRepliesByCustomer(List<String> wpasTtlRepliesByCustomer) {
        this.wpasTtlRepliesByCustomer = wpasTtlRepliesByCustomer;
    }

    public List<String> getWpasTtlRepliesByAgent() {
        return wpasTtlRepliesByAgent;
    }

    public void setWpasTtlRepliesByAgent(List<String> wpasTtlRepliesByAgent) {
        this.wpasTtlRepliesByAgent = wpasTtlRepliesByAgent;
    }

    public List<String> getWpasTtlReplies() {
        return wpasTtlReplies;
    }

    public void setWpasTtlReplies(List<String> wpasTtlReplies) {
        this.wpasTtlReplies = wpasTtlReplies;
    }

    public List<String> getWpasLastReplyDate() {
        return wpasLastReplyDate;
    }

    public void setWpasLastReplyDate(List<String> wpasLastReplyDate) {
        this.wpasLastReplyDate = wpasLastReplyDate;
    }

    public List<String> getWpasLastReplyDateGmt() {
        return wpasLastReplyDateGmt;
    }

    public void setWpasLastReplyDateGmt(List<String> wpasLastReplyDateGmt) {
        this.wpasLastReplyDateGmt = wpasLastReplyDateGmt;
    }

    public List<String> getWpasIsWaitingClientReply() {
        return wpasIsWaitingClientReply;
    }

    public void setWpasIsWaitingClientReply(List<String> wpasIsWaitingClientReply) {
        this.wpasIsWaitingClientReply = wpasIsWaitingClientReply;
    }

    public List<String> getAutoDeleteAttachments() {
        return autoDeleteAttachments;
    }

    public void setAutoDeleteAttachments(List<String> autoDeleteAttachments) {
        this.autoDeleteAttachments = autoDeleteAttachments;
    }

    public List<String> getAutoDeleteAttachmentsType() {
        return autoDeleteAttachmentsType;
    }

    public void setAutoDeleteAttachmentsType(List<String> autoDeleteAttachmentsType) {
        this.autoDeleteAttachmentsType = autoDeleteAttachmentsType;
    }

}
