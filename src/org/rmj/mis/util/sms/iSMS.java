package org.rmj.mis.util.sms;

import org.rmj.appdriver.agent.GRiderX;

public interface iSMS {
    public void setGRider(GRiderX foValue);
    public boolean Process();
    public int getItemCount();
    public int getInvalid();
    
    public void setMessage(String fsValue);
    public String getMessage();
}
