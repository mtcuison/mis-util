package org.rmj.mis.util.factory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agent.GRiderX;
import org.rmj.appdriver.agentfx.CommonUtils;

public class TLM_Leads implements UtilityValidator{
    private final int pxe2Fill = 100;
    private final int pxePriorityFill = 20;
    private final String pxeInquiry = "2021-05-01";
    
    private GRiderX instance;
    private String sMessage;
    
    @Override
    public void setGRider(GRiderX foValue) {
        instance = foValue;
        
        if (instance == null){
            System.err.println("Application driver is not set.");
            System.exit(1);
        }
    }

    @Override
    public boolean Run() {        
        //top priority MC Facebook Inquiries
        int lnTotal = 0;
        int lnFilled = processLeads(fill_sched_from_inquiry("MC", "FB", SQLUtil.toDate(pxeInquiry, SQLUtil.FORMAT_SHORT_DATE)), pxePriorityFill, "0");
        System.out.println("System inserted " + lnFilled + " facebook inquiries from MC. - GLOBE");
        lnTotal += lnFilled;
        
        lnFilled = processLeads(fill_sched_from_inquiry("MC", "FB", SQLUtil.toDate(pxeInquiry, SQLUtil.FORMAT_SHORT_DATE)), pxePriorityFill, "1");
        System.out.println("System inserted " + lnFilled + " facebook inquiries from MC. - SMART");
        lnTotal += lnFilled;
        
        lnFilled = processLeads(fill_sched_from_inquiry("MC", "FB", SQLUtil.toDate(pxeInquiry, SQLUtil.FORMAT_SHORT_DATE)), pxePriorityFill, "2");
        System.out.println("System inserted " + lnFilled + " facebook inquiries from MC. - SUN");
        lnTotal += lnFilled;
        System.out.println("System inserted total of " + lnTotal + " facebook inquiries from MC. - ALL NETWORKS");
        
        //top priority MP Facebook Inquiries
        lnTotal = 0;
        lnFilled = processLeads(fill_sched_from_inquiry("MP", "FB", SQLUtil.toDate(pxeInquiry, SQLUtil.FORMAT_SHORT_DATE)), pxePriorityFill, "0");
        System.out.println("System inserted " + lnFilled + " facebook inquiries from MP. - GLOBE");
        lnTotal += lnFilled;
        
        lnFilled = processLeads(fill_sched_from_inquiry("MP", "FB", SQLUtil.toDate(pxeInquiry, SQLUtil.FORMAT_SHORT_DATE)), pxePriorityFill, "1");
        System.out.println("System inserted " + lnFilled + " facebook inquiries from MP. - SMART");
        lnTotal += lnFilled;
        
        lnFilled = processLeads(fill_sched_from_inquiry("MP", "FB", SQLUtil.toDate(pxeInquiry, SQLUtil.FORMAT_SHORT_DATE)), pxePriorityFill, "2");
        System.out.println("System inserted " + lnFilled + " facebook inquiries from MP. - SUN");
        lnTotal += lnFilled;
        System.out.println("System inserted total of " + lnTotal + " facebook inquiries from MP. - ALL NETWORKS");
        
        //other product inquiries
        
        int n2Fill = get2Fill(pxe2Fill, "0");
        if (n2Fill > pxePriorityFill) n2Fill = pxePriorityFill;
        System.out.println("For filling - globe:" + n2Fill);
        if(n2Fill > 0) fill_sched(n2Fill, "0");
         
        n2Fill = get2Fill(pxe2Fill, "1");
        if (n2Fill > pxePriorityFill) n2Fill = pxePriorityFill;
        System.out.println("For filling - smart:" + n2Fill);
        if(n2Fill > 0) fill_sched(n2Fill, "1");

        n2Fill = get2Fill(pxe2Fill, "2");
        if (n2Fill > pxePriorityFill) n2Fill = pxePriorityFill;
        System.out.println("For filling - sun:" + n2Fill);
        if(n2Fill > 0) fill_sched(n2Fill, "2");
        
        return true;
    }

    @Override
    public void setMessage(String fsValue) {
        sMessage = fsValue;
    }

    @Override
    public String getMessage() {
        return sMessage;
    }
    
    /**
    * get2Fill(int n2Fill, String lcSubScribe)
    * 
    * mac 2019.09.16
    *   added source codes
    *       MCCA - approved MC Credit Application
    *       MPIn - mobile phone inquiry
    * mac 2019.10.03
    *   added source codes
    *       GBF - Guanzon Byaheng Fiesta
    *       FSCU - Free Service Check Up
    *       DC - Display Caravan
    *       OTH - Other activity
    */
    private int get2Fill(int n2Fill, String cSubscrbr){
        String lsSQL;
        lsSQL = "SELECT COUNT(*) nRecCount"
                    + " FROM Call_Outgoing"
                    + " WHERE cTranStat IN ('0', '1')" 
                        + " AND sSourceCD NOT IN (" +
                                "'LEND', 'MCSO', 'MCCA', 'MPIn', 'GBF', 'FSCU', 'DC', 'OTH', 'INQR'" + ")";   
      
        switch (cSubscrbr) {
            case "0":
                lsSQL = lsSQL + " AND cSubscrbr = " + SQLUtil.toSQL(cSubscrbr);
                break;
            case "1":
                lsSQL = lsSQL + " AND cSubscrbr = " + SQLUtil.toSQL(cSubscrbr);      
                break;
            case "2":
                lsSQL = lsSQL + " AND cSubscrbr = " + SQLUtil.toSQL(cSubscrbr);
                break;
            default:
                lsSQL = lsSQL + " AND 0=1";
        }
      
        try {
            ResultSet loRS = instance.executeQuery(lsSQL);
         
            loRS.next();
            return n2Fill - loRS.getInt("nRecCount");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    //mac 2021.05.22
    //  separate retreival of FB Inquiries to set as top priority on TLM Leads
    private ResultSet fill_sched_from_inquiry(String fsDivision, String fsInquryTp, Date ldStart){
        String lsSQL;
        
        switch (fsDivision.toLowerCase()){
            case "mc":
                lsSQL = "MC_Product_Inquiry";
                break;
            case "mp":
                lsSQL = "MP_Product_Inquiry";
                break;
            default:
                return null;
        }
        
        if (fsInquryTp.toLowerCase().equals("fb")){
            lsSQL = "SELECT '' sMobileNo, sClientID, dFollowUp, sTransNox, '" + lsSQL + "' sTableNme, dTransact, sCreatedx" + 
                        " FROM " + lsSQL +
                        " WHERE cTranStat = '0'" +
                            " AND dTransact >= " + SQLUtil.toSQL(ldStart) +
                            " AND sInquiryx = " + SQLUtil.toSQL(fsInquryTp) +
                            " AND(" +
                                " (dFollowUp BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) AND DATE_ADD(CURRENT_TIMESTAMP(), INTERVAL 10 MINUTE))" +
                                    " OR (dTargetxx BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) AND DATE_ADD(CURRENT_TIMESTAMP(), INTERVAL 10 MINUTE) AND dFollowUp IS NULL)" +
                                    " OR (sInquiryx = 'WI' AND @xTransact >= dTransact AND dTargetxx IS NULL AND dFollowUp IS NULL)" +
                                " )" +
                        " ORDER BY dFollowUp DESC, dTargetxx DESC, dTransact DESC";
        } else {
            lsSQL = "SELECT '' sMobileNo, sClientID, dFollowUp, sTransNox, '" + lsSQL + "' sTableNme, dTransact, sCreatedx" + 
                        " FROM " + lsSQL +
                        " WHERE cTranStat = '0'" +
                            " AND dTransact >= " + SQLUtil.toSQL(ldStart) +
                            " AND sInquiryx <> " + SQLUtil.toSQL(fsInquryTp) +
                            " AND(" +
                                " (dFollowUp BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) AND DATE_ADD(CURRENT_TIMESTAMP(), INTERVAL 10 MINUTE))" +
                                    " OR (dTargetxx BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) AND DATE_ADD(CURRENT_TIMESTAMP(), INTERVAL 10 MINUTE) AND dFollowUp IS NULL)" +
                                    " OR (sInquiryx = 'WI' AND @xTransact >= dTransact AND dTargetxx IS NULL AND dFollowUp IS NULL)" +
                                " )" +
                        " ORDER BY dFollowUp DESC, dTargetxx DESC, dTransact DESC";
        }
        
        
        return instance.executeQuery(lsSQL);
    }
    
    
    //Retrieve records to be scheduled...
    //===================================
    private void fill_sched(int n2Fill, String lcSubScribe){        
        StringBuilder lsSQL = new StringBuilder();
        
        //kalyptus - 2017.10.10 09:17am
        //load MC_Product_Inquiry: 2 DAYS AGO upto 10 minutes before the scheduled followup...
        //                 and if Walk inquiry after 7 days ago... Previously it was 60 days ago.
        lsSQL.append(" SELECT '' sMobileNo, sClientID, dFollowUp, dTargetxx, sTransNox, 'MC_Product_Inquiry' sTableNme, dTransact, sCreatedx, @xTransact :=  DATE_ADD(CURRENT_DATE(), INTERVAL - 8 DAY) xTransact"
                    + " FROM MC_Product_Inquiry"
                    + " WHERE cTranStat = '0'"
                        + " AND sInquiryx <> 'FB'"
                        + " AND("
                        + "    (dFollowUp BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) AND DATE_ADD(CURRENT_TIMESTAMP(), INTERVAL 10 MINUTE))" +
                            "    OR (dTargetxx BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) AND DATE_ADD(CURRENT_TIMESTAMP(), INTERVAL 10 MINUTE) AND dFollowUp IS NULL)" +
                            "    OR (sInquiryx = 'WI' AND @xTransact >= dTransact AND dTargetxx IS NULL AND dFollowUp IS NULL)" +
                            "   )" +
                            "   AND dTransact >= '2020-01-01'");

        //load MP_Product_Inquiry: 2 DAYS AGO upto 10 minutes before the scheduled followup...
        //                 and if Walk inquiry after 7 days ago... Previously it was 60 days ago.
        lsSQL.append(" UNION ");
        lsSQL.append(" SELECT '' sMobileNo, sClientID, dFollowUp, dTargetxx, sTransNox, 'MP_Product_Inquiry' sTableNme, dTransact, sCreatedx, @xTransact :=  DATE_ADD(CURRENT_DATE(), INTERVAL - 8 DAY) xTransact"
                    + " FROM MP_Product_Inquiry"
                    + " WHERE cTranStat = '0'"
                        + " AND sInquiryx <> 'FB'"
                        + " AND("
                        + "    (dFollowUp BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) AND DATE_ADD(CURRENT_TIMESTAMP(), INTERVAL 10 MINUTE))" +
                            "    OR (dTargetxx BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) AND DATE_ADD(CURRENT_TIMESTAMP(), INTERVAL 10 MINUTE) AND dFollowUp IS NULL)" +
                            "    OR (sInquiryx = 'WI' AND @xTransact >= dTransact AND dTargetxx IS NULL AND dFollowUp IS NULL)" +
                            "   )" +
                            "   AND dTransact >= '2020-01-01'");

        //load MC_Referral: 2 DAYS AGO upto 10 minutes before the scheduled followup...
        lsSQL.append(" UNION ");
        lsSQL.append(" SELECT '' sMobileNo, sClientID, dFollowUp, dTargetxx, sTransNox, 'MC_Referral' sTableNme, dTransact, '' sCreatedx, @xTransact :=  DATE_ADD(CURRENT_DATE(), INTERVAL - 8 DAY) xTransact"
                    + " FROM MC_Referral"
                    + " WHERE cTranStat = '0'"
                        + " AND (dFollowUp BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) AND DATE_ADD(CURRENT_TIMESTAMP(), INTERVAL 10 MINUTE) OR dFollowUp IS NULL)");

        //load Call_Incoming: 2 DAYS AGO upto 10 minutes before the scheduled followup...
        lsSQL.append(" UNION ");
        lsSQL.append(" SELECT sMobileNo, '' sClientID, dFollowUp, NULL dTargetxx, sTransNox, 'Call_Incoming' sTableNme, dTransact, '' sCreatedx, @xTransact :=  DATE_ADD(CURRENT_DATE(), INTERVAL - 8 DAY) xTransact"
                    + " FROM Call_Incoming"
                    + " WHERE cTranStat = '0'"
                        + " AND (dFollowUp BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) AND DATE_ADD(CURRENT_TIMESTAMP(), INTERVAL 10 MINUTE) OR dFollowUp IS NULL)"
                        + " AND sMobileNo <> ''");

        //load SMS_Incoming: 2 DAYS AGO upto 10 minutes before the scheduled followup...
        lsSQL.append(" UNION ");
        lsSQL.append(" SELECT sMobileNo, '' sClientID, dFollowUp, NULL dTargetxx, sTransNox, 'SMS_Incoming' sTableNme, dTransact, '' sCreatedx, @xTransact :=  DATE_ADD(CURRENT_DATE(), INTERVAL - 8 DAY) xTransact"
                    + " FROM SMS_Incoming"
                    + " WHERE cTranStat = '0'"
                        + " AND cReadxxxx = '1'"
                        + " AND (dFollowUp BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) AND DATE_ADD(CURRENT_TIMESTAMP(), INTERVAL 10 MINUTE) OR dFollowUp IS NULL)"
                        + " AND sMessagex NOT LIKE '%FSE%'");

        //load SMS_Incoming: 2 DAYS AGO upto 10 minutes before the scheduled followup...
        //kalyptus - 2018.04.18 10:59am
        //contact MP costumer only...
        lsSQL.append(" UNION ");
        lsSQL.append(" (SELECT '' sMobileNo, sClientID, dFollowUp, NULL dTargetxx, sClientID sTransNox, 'TLM_Client' sTableNme, dFollowUp dTransact, '' sCreatedx, @xTransact :=  DATE_ADD(CURRENT_DATE(), INTERVAL - 8 DAY) xTransact"
                    + " FROM TLM_Client"
                    + " WHERE cTranStat = '0'"
                        + " AND cSourceCd = 'MP'" 
                        + " AND (dFollowUp BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY) AND DATE_ADD(CURRENT_TIMESTAMP(), INTERVAL 10 MINUTE)"
                        +       " OR dFollowUp IS NULL))");

        lsSQL.append(" ORDER BY dFollowUp DESC, dTargetxx DESC, dTransact DESC");
        
        ResultSet loRS = instance.executeQuery(lsSQL.toString());
        processLeads(loRS, n2Fill, lcSubScribe);
    }
    
    private int processLeads(ResultSet foResult, int n2Fill, String lcSubScribe){
        StringBuilder lsSQL = new StringBuilder();
        int lnCtr = 0;
        
        try {
            ResultSet loRS = foResult;
            
            String lsMobileNo;
            String lsClientID;
            String lsSourceCD;
            String lcSubscrbr;
            String lsCreatedx;

            instance.beginTrans();
            
            while(loRS.next() && lnCtr < n2Fill){
                System.out.println("TABLE:" + loRS.getString("sTableNme")); 
                if(loRS.getString("sTableNme").equalsIgnoreCase("MC_Product_Inquiry")){
                    lsSourceCD = "INQR";
                    lsClientID = loRS.getString("sClientID");
                    lsMobileNo = getMobile(loRS.getString("sClientID"), loRS.getString("sTransNox"));
                    lcSubscrbr = "";
                    lsCreatedx = "";
                    
                    if (loRS.getString("sCreatedx") != null)
                        if (loRS.getString("sTransNox").substring(0, 4).toLowerCase().equals("m0t1")) 
                            lsCreatedx = loRS.getString("sCreatedx");
                    else
                        lsCreatedx = getAgentIDx(lsSourceCD, loRS.getString("sTransNox"));    
                } 
                else if(loRS.getString("sTableNme").equalsIgnoreCase("MP_Product_Inquiry")){
                    lsSourceCD = "MPIn";
                    lsClientID = loRS.getString("sClientID");
                    lsMobileNo = getMobile(loRS.getString("sClientID"), loRS.getString("sTransNox"));
                    lsCreatedx = "";
                    
                    if (loRS.getString("sCreatedx") != null)
                        if (loRS.getString("sTransNox").substring(0, 4).toLowerCase().equals("m0t1")) 
                            lsCreatedx = loRS.getString("sCreatedx");
                    else
                        lsCreatedx = getAgentIDx(lsSourceCD, loRS.getString("sTransNox"));   
                }
                else if(loRS.getString("sTableNme").equalsIgnoreCase("MC_Referral")){
                    lsSourceCD = "RFRL";
                    lsClientID = loRS.getString("sClientID");
                    lsMobileNo = getMobile(loRS.getString("sClientID"), loRS.getString("sTransNox"));
                    lcSubscrbr = "";
                    lsCreatedx = getAgentIDx(lsSourceCD, loRS.getString("sTransNox"));
                }
                else if(loRS.getString("sTableNme").equalsIgnoreCase("Call_Incoming")){
                    lsSourceCD = "CALL";
                    lsClientID = getClient(loRS.getString("sMobileNo"), loRS.getString("sTransNox"));
                    lsMobileNo = loRS.getString("sMobileNo");
                    lcSubscrbr = "";
                    lsCreatedx = getAgentIDx(lsSourceCD, loRS.getString("sTransNox"));
                }
                else if(loRS.getString("sTableNme").equalsIgnoreCase("SMS_Incoming")){
                    lsSourceCD = "ISMS";
                    lsClientID = getClient(loRS.getString("sMobileNo"), loRS.getString("sTransNox"));
                    lsMobileNo = loRS.getString("sMobileNo");
                    lcSubscrbr = "";
                    lsCreatedx = getAgentIDx(lsSourceCD, loRS.getString("sTransNox"));
                }
                else if(loRS.getString("sTableNme").equalsIgnoreCase("TLM_Client")){
                    lsSourceCD = "TLMC";
                    lsClientID = loRS.getString("sClientID");
                    lsMobileNo = getMobile(loRS.getString("sClientID"), loRS.getString("sTransNox"));
                    lcSubscrbr = "";
                    lsCreatedx = getAgentIDx(lsSourceCD, loRS.getString("sTransNox"));
                }            
                else{
                    lsSourceCD = "";
                    lsClientID = "";
                    lsMobileNo = "";
                    lcSubscrbr = "";
                    lsCreatedx = "";
                }

                if(lsMobileNo.length() > 5){
                    System.out.println("Classify Moble for: " + lsClientID + "»" + lsMobileNo);
                    lcSubscrbr = CommonUtils.classifyNetwork(lsMobileNo);
    
                    if(lcSubScribe.equals(lcSubscrbr)){
                        //System.out.println("Checking if number has outgoing call!" + lsMobileNo);
                        //Load from Call_Outgoing if the retrieved number has a pending schedule
                        lsSQL = new StringBuilder();
                        lsSQL.append("SELECT *"
                                  + " FROM Call_Outgoing"
                                  + " WHERE sMobileNo = " + SQLUtil.toSQL(lsMobileNo)
                                    + " AND cTranStat IN ('0', '1')");
                        ResultSet loRSX = instance.executeQuery(lsSQL.toString());

                        //Create a schedule if number has no pending schedule
                        if(!loRSX.next()) {
                            System.out.println("Number has no outgoing call!" + lsMobileNo);
                            lsSQL = new StringBuilder();
                            
                            if (lsCreatedx.isEmpty())
                                lsSQL.append("INSERT INTO Call_Outgoing" 
                                            + " SET sTransNox = " + SQLUtil.toSQL(MiscUtil.getNextCode("Call_Outgoing", "sTransNox", true, instance.getConnection(), instance.getBranchCode()))
                                            + ", dTransact = " + SQLUtil.toSQL(instance.getServerDate()) 
                                            + ", sClientID = " + SQLUtil.toSQL(lsClientID)
                                            + ", sMobileNo = " + SQLUtil.toSQL(lsMobileNo)
                                            + ", sRemarksx = ''" 
                                            + ", sReferNox = " + SQLUtil.toSQL(loRS.getString("sTransNox"))
                                            + ", sSourceCD = " + SQLUtil.toSQL(lsSourceCD)
                                            + ", cTranStat = '0'" 
                                            + ", sAgentIDx = ''" 
                                            + ", nNoRetryx = 0"
                                            + ", cSubscrbr = " + SQLUtil.toSQL(lcSubscrbr)
                                            + ", cCallStat = '0'"
                                            + ", cTLMStatx = '0'"
                                            + ", cSMSStatx = '0'" 
                                            + ", nSMSSentx = 0" 
                                            + ", sModified = " + SQLUtil.toSQL(instance.getUserID())
                                            + ", dModified = " + SQLUtil.toSQL(instance.getServerDate()));
                            else
                                lsSQL.append("INSERT INTO Call_Outgoing" 
                                            + " SET sTransNox = " + SQLUtil.toSQL(MiscUtil.getNextCode("Call_Outgoing", "sTransNox", true, instance.getConnection(), instance.getBranchCode()))
                                            + ", dTransact = " + SQLUtil.toSQL(instance.getServerDate()) 
                                            + ", sClientID = " + SQLUtil.toSQL(lsClientID)
                                            + ", sMobileNo = " + SQLUtil.toSQL(lsMobileNo)
                                            + ", sRemarksx = ''" 
                                            + ", sReferNox = " + SQLUtil.toSQL(loRS.getString("sTransNox"))
                                            + ", sSourceCD = " + SQLUtil.toSQL(lsSourceCD)
                                            + ", cTranStat = '1'" 
                                            + ", sAgentIDx = " + SQLUtil.toSQL(lsCreatedx) 
                                            + ", nNoRetryx = 0"
                                            + ", cSubscrbr = " + SQLUtil.toSQL(lcSubscrbr)
                                            + ", cCallStat = '0'"
                                            + ", cTLMStatx = '0'"
                                            + ", cSMSStatx = '0'" 
                                            + ", nSMSSentx = 0" 
                                            + ", sModified = " + SQLUtil.toSQL(instance.getUserID())
                                            + ", dModified = " + SQLUtil.toSQL(instance.getServerDate()));

                            long count = instance.executeQuery(lsSQL.toString(), "Call_Outgoing", "", "");

                            if(count == 0){
                                System.err.println(instance.getMessage() + instance.getErrMsg());
                                instance.rollbackTrans();
                                System.exit(1);
                            }

                            lnCtr++;

                            if(loRS.getString("sTableNme").equalsIgnoreCase("TLM_Client")) {
                                //Tagged the source as scheduled
                                lsSQL = new StringBuilder();
                                lsSQL.append("UPDATE " + loRS.getString("sTableNme")
                                            + " SET cTranStat = '1'" 
                                            + " WHERE sClientID = " + SQLUtil.toSQL(loRS.getString("sTransNox")));
                                instance.executeQuery(lsSQL.toString(), loRS.getString("sTableNme"), "", "");
                            } else{
                                //Tagged the source as scheduled
                                lsSQL = new StringBuilder();
                                lsSQL.append("UPDATE " + loRS.getString("sTableNme")
                                        + " SET cTranStat = '1'" 
                                        + " WHERE sTransNox = " + SQLUtil.toSQL(loRS.getString("sTransNox")));
                                instance.executeQuery(lsSQL.toString(), loRS.getString("sTableNme"), "", "");
                            }
                        }
                        // kalyptus - 2016.08.01 13.45pm
                        // a number has a pending schedule so set 
                        else{
                            System.out.println("Number has outgoing call!" + lsMobileNo);

                            if(loRS.getString("sTableNme").equalsIgnoreCase("TLM_Client")) {
                                //Tagged the source do not schedule
                                lsSQL = new StringBuilder();
                                lsSQL.append("UPDATE " + loRS.getString("sTableNme")
                                            + " SET cTranStat = '4'" 
                                            + " WHERE sClientID = " + SQLUtil.toSQL(loRS.getString("sTransNox")));
                                instance.executeQuery(lsSQL.toString(), loRS.getString("sTableNme"), "", "");
                            }
                            else{
                                //Tagged the source do not schedule
                                lsSQL = new StringBuilder();
                                lsSQL.append("UPDATE " + loRS.getString("sTableNme")
                                            + " SET cTranStat = '4'" 
                                            + " WHERE sTransNox = " + SQLUtil.toSQL(loRS.getString("sTransNox")));
                                instance.executeQuery(lsSQL.toString(), loRS.getString("sTableNme"), "", "");
                            }
                        } //if(!loRSX.next())
                    } //if(bglobe == lbIsGlobe)
                } //if(lsMobileNo.length() > 5)
            } //while(loRS.next() && lnCtr < n2Fill)
         
            instance.commitTrans();
         
        } catch (SQLException ex) {
            ex.printStackTrace();
            instance.rollbackTrans();
            System.exit(1);
        }
        
        return lnCtr;
    }
   
    private String getMobile(String fsClientID, String fsTransNox){
        String lsSQL;
        ResultSet loRS;
        
        System.out.println(fsClientID + "»" + fsTransNox);
        
        if(!fsClientID.equalsIgnoreCase(fsTransNox) ){
            //Get previous call Call_Outgoing;
            lsSQL = "SELECT sMobileNo" +
                    " FROM Call_Outgoing" +
                    " WHERE sClientID = " + SQLUtil.toSQL(fsClientID) +
                        " AND sTransNox <> " + SQLUtil.toSQL(fsTransNox) + 
                    " ORDER BY dTransact DESC" +
                    " LIMIT 1";
            try {
                loRS = instance.executeQuery(lsSQL.toString());
                if(loRS.next()) return loRS.getString("sMobileNo");
            } catch (SQLException ex) {
                ex.printStackTrace();
                return "";
            }
        }
      
        //Get Mobile from Client_Master
        lsSQL = "SELECT sMobileNo" +
                " FROM Client_Master" +
                " WHERE sClientID = " + SQLUtil.toSQL(fsClientID) +
                " AND cRecdStat = '1'";
        try {
            loRS = instance.executeQuery(lsSQL.toString());
            if(loRS.next())
                if(!loRS.getString("sMobileNo").equals(""))
                    return loRS.getString("sMobileNo");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "";
        }

        //Get Mobile from Client_Mobile
        lsSQL = "SELECT sMobileNo" +
                " FROM Client_Mobile" +
                " WHERE sClientID = " + SQLUtil.toSQL(fsClientID) +
                    " AND cRecdStat = '1'" + 
                " ORDER BY nPriority ASC";
        try {
            loRS = instance.executeQuery(lsSQL.toString());

            if(loRS.next()) return loRS.getString("sMobileNo");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "";
        }
      
        return "";
    }
    
    private String getClient(String fsMobileNo, String fsTransNox){
        String lsSQL;
        ResultSet loRS;
      
        //Get previous call Call_Outgoing;
        lsSQL = "SELECT sClientID" +
                " FROM Call_Outgoing" +
                " WHERE sMobileNo = " + SQLUtil.toSQL(fsMobileNo) + 
                    " AND sTransNox <> " + SQLUtil.toSQL(fsTransNox) +
                " ORDER BY dTransact DESC" + 
                " LIMIT 1";
        try {
            loRS = instance.executeQuery(lsSQL.toString());
            
            if(loRS.next()) return loRS.getString("sClientID");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "";
        }
      
        //Get Mobile from Client_Master
        lsSQL = "SELECT sClientID" +
                " FROM Client_Master" +
                " WHERE sMobileNo = " + SQLUtil.toSQL(fsMobileNo) +
                    " AND cRecdStat = '1'";
        try {
            loRS = instance.executeQuery(lsSQL.toString());

            if(loRS.last()){
                if(loRS.getRow() == 1){
                   loRS.first();
                   return loRS.getString("sClientID");
                } 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "";         
        }

        //Get Mobile from Client_Mobile
        lsSQL = "SELECT sClientID" +
                " FROM Client_Mobile" +
                " WHERE sMobileNo = " + SQLUtil.toSQL(fsMobileNo) +
                    " AND cRecdStat = '1'";
        try {
            loRS = instance.executeQuery(lsSQL.toString());

            if(loRS.last()){
                if(loRS.getRow() == 1){
                    loRS.first();
                    return loRS.getString("sClientID");
                } 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "";
        }
      
        return "";
    }
    
    //get the last agent called the transaction
    private String getAgentIDx(String fsSourceCD, String fsReferNox){
        String lsSQL = "SELECT sAgentIDx FROM Call_Outgoing" +
                        " WHERE sSourceCd = " + SQLUtil.toSQL(fsSourceCD) +
                            " AND sReferNox = " + SQLUtil.toSQL(fsReferNox) +
                            " AND cTranStat = '2'" +
                        " ORDER BY sTransNox DESC LIMIT 1";
        
        ResultSet loRS = instance.executeQuery(lsSQL);
        
        try {
            if (loRS.next())
                return loRS.getString("sAgentIDx");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
