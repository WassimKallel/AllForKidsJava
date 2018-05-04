/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.orderManagement.models;

import dopsie.core.*;

/**
 *
 * @author KHOUBEIB
 */
public class Payment extends Model {

    @Override
    public String getTableName() {
        return "`payment`";
    }

    @Override
    public String getPrimaryKeyName() {
        return "id";
    }

   public enum PaymentStatus {
        DONE("Done"), DUE("Due"), PARTIAL("Partial");
        private String status;

        PaymentStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return status;
        }
    };

  public  enum PaymentMethod {
        PAYPAL("PayPal"), BITCOIN("BitCoin"), CREDITCARD("Credit Card"), CREDITPOINT("CreditPoint");

        private String method;

        PaymentMethod(String method) {
            this.method = method;
        }
        
        public String paymentMethodName() {
            return method;
        }
    };
    


    public Double getPaymentAmount() {

        return (Double) this.getAttr("amount");
    }

    private PaymentMethod method;
    private PaymentStatus status;

    public void setPaymentMethod(PaymentMethod method) {
        this.setAttr("method", method.name());
    }

}
