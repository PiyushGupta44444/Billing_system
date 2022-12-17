public class Invoices {
    private int invoiceId;
    private int totalBill;
    private String paymentStatus;
    public int getInvoiceId() {
        return invoiceId;
    }
    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }
    public int getTotalBill() {
        return totalBill;
    }
    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
