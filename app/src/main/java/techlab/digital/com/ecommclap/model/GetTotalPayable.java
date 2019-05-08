package techlab.digital.com.ecommclap.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTotalPayable{
    @SerializedName("current_wallet_balance")
    @Expose
    private String currentWalletBalance;
    @SerializedName("current_total_reward_point")
    @Expose
    private String currentTotalRewardPoint;
    @SerializedName("Point_appleid")
    @Expose
    private Boolean pointAppleid;
    @SerializedName("total_deduction")
    @Expose
    private String totalDeduction;
    @SerializedName("total_payable_amount")
    @Expose
    private String totalPayableAmount;

    public String getCurrentWalletBalance() {
        return currentWalletBalance;
    }

    public void setCurrentWalletBalance(String currentWalletBalance) {
        this.currentWalletBalance = currentWalletBalance;
    }

    public String getCurrentTotalRewardPoint() {
        return currentTotalRewardPoint;
    }

    public void setCurrentTotalRewardPoint(String currentTotalRewardPoint) {
        this.currentTotalRewardPoint = currentTotalRewardPoint;
    }

    public Boolean getPointAppleid() {
        return pointAppleid;
    }

    public void setPointAppleid(Boolean pointAppleid) {
        this.pointAppleid = pointAppleid;
    }

    public String getTotalDeduction() {
        return totalDeduction;
    }

    public void setTotalDeduction(String totalDeduction) {
        this.totalDeduction = totalDeduction;
    }

    public String getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(String totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }
}