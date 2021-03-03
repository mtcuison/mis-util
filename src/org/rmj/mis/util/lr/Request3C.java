/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rmj.mis.util.lr;

import java.util.ArrayList;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.MiscUtil;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agentfx.CommonUtils;

/**
 *
 * @author mac
 */
public class Request3C {
    public static void main (String [] args){
        final String PRODUCTID = "gRider";
        final String USERID = "M001111122";
        
        GRider poGRider = new GRider(PRODUCTID);

        if (!poGRider.loadEnv(PRODUCTID)) {
            System.err.println(poGRider.getMessage());
            System.err.println(poGRider.getErrMsg());
            MiscUtil.close(poGRider.getConnection());
            System.exit(1);
        }
        if (!poGRider.logUser(PRODUCTID, USERID)) {
            System.err.println(poGRider.getMessage());
            System.err.println(poGRider.getErrMsg());
            MiscUtil.close(poGRider.getConnection());
            System.exit(1);
        }
        
        ArrayList loArr = getAccounts();
        String lsSQL;
        
        if (!loArr.isEmpty()){
            poGRider.beginTrans();
            int lnCtr;
            for (lnCtr = 0; lnCtr <= loArr.size()-1; lnCtr++){
                lsSQL = "INSERT INTO LR_3C_Request SET" +
                            "  sTransNox = " + SQLUtil.toSQL(MiscUtil.getNextCode("LR_3C_Request", "sTransNox", true, poGRider.getConnection(), poGRider.getBranchCode())) +
                            ", dTransact = '2021-02-20'" + 
                            ", dCollDate = '2021-02-20'" + 
                            ", sAcctNmbr = " + SQLUtil.toSQL(loArr.get(lnCtr)) + 
                            ", sMobileNo = ''" +
                            ", sRequestx = 'M00109011794'" + 
                            ", sApproved = ''" + 
                            ", sRemarksx = ''" + 
                            ", cTranStat = '0'" + 
                            ", sModified = " + SQLUtil.toSQL(poGRider.getUserID()) +
                            ", dModified = " + SQLUtil.toSQL(poGRider.getServerDate());

                if (poGRider.executeQuery(lsSQL, "LR_3C_Request", poGRider.getBranchCode(), "") <= 0){
                    poGRider.rollbackTrans();
                    System.err.println(poGRider.getErrMsg() + "; " + poGRider.getMessage());
                    System.exit(1);
                }
            }
            poGRider.commitTrans();
            
            System.out.print("Accounts have 3C request was created successfully. " + lnCtr);
        } else 
            System.out.print("No accounts detected.");
    }
    
    private static ArrayList getAccounts(){
        ArrayList instance = new ArrayList();
        
        instance.add("M120190158");
instance.add("M063200061");
instance.add("M063200140");
instance.add("M063200071");
instance.add("M146200171");
instance.add("M107180178");
instance.add("M084200016");
instance.add("M107190182");
instance.add("M146200108");
instance.add("M093200106");
instance.add("M133180030");
instance.add("M133200029");
instance.add("M130200110");
instance.add("M017200106");
instance.add("M017200171");
instance.add("M017180258");
instance.add("M093180180");
instance.add("M109200046");
instance.add("M007200198");
instance.add("M093200041");
instance.add("M093190309");
instance.add("M146200070");
instance.add("M146190178");
instance.add("M146200173");
instance.add("M079200071");
instance.add("M079200046");
instance.add("M079190037");
instance.add("M079200088");
instance.add("M079200089");
instance.add("M079190115");
instance.add("M090180097");
instance.add("M090200040");
instance.add("M090200031");
instance.add("M027190081");
instance.add("M078200083");
instance.add("M115200059");
instance.add("M107190111");
instance.add("M052190059");
instance.add("M052190078");
instance.add("M050190036");
instance.add("M122200073");
instance.add("M002200017");
instance.add("M001180206");
instance.add("M084190248");
instance.add("M107200026");
instance.add("M084200013");
instance.add("M012200064");
instance.add("M118200037");
instance.add("M118200050");
instance.add("M118190113");
instance.add("M118190093");
instance.add("M118190019");
instance.add("M001180299");
instance.add("M014200101");
instance.add("M014190050");
instance.add("M042190101");
instance.add("M042190149");
instance.add("M042200012");
instance.add("M087200113");
instance.add("M045200100");
instance.add("M077200103");
instance.add("M045200034");
instance.add("M066200158");
instance.add("M085180123");
instance.add("M066200106");
instance.add("M006190096");
instance.add("M021200093");
instance.add("M100190128");
instance.add("M100190063");
instance.add("M100190136");
instance.add("M100190032");
instance.add("M125190137");
instance.add("M100200062");
instance.add("M125190101");
instance.add("M071200107");
instance.add("M095190162");
instance.add("M095200052");
instance.add("M095200078");
instance.add("M071180181");
instance.add("M071200055");
instance.add("M095200174");
instance.add("M071190210");
instance.add("M071190189");
instance.add("M0711X0384");
instance.add("M095200006");
instance.add("M071180106");
instance.add("M095190164");
instance.add("M095200144");
instance.add("M0951X0488");
instance.add("M095190021");
instance.add("M071200089");
instance.add("M095200159");
instance.add("M095190183");
instance.add("M071200187");
instance.add("M071200036");
instance.add("M095190016");
instance.add("M095190015");
instance.add("M095200122");
instance.add("M071200064");
instance.add("M071190180");
instance.add("M095200170");
instance.add("M095200053");
instance.add("M095190133");
instance.add("M095190120");
instance.add("M095200142");
instance.add("M071200085");
instance.add("M095190257");
instance.add("M095190102");
instance.add("M095200125");
instance.add("M095200100");
instance.add("M095200074");
instance.add("M095190230");
instance.add("M095200124");
instance.add("M095200162");
instance.add("M071200040");
instance.add("M038200074");
instance.add("M038200129");
instance.add("M132190128");
instance.add("M132190229");
instance.add("M143200043");
instance.add("M143190061");
instance.add("M143190049");
instance.add("M143200023");
instance.add("M143200049");
instance.add("M143190043");
instance.add("M071200211");
instance.add("M071190263");
instance.add("M071190292");
instance.add("M071190192");
instance.add("M095200194");
instance.add("M071180285");
instance.add("M095200011");
instance.add("M071190284");
instance.add("M071180338");
instance.add("M071180167");
instance.add("M071200122");
instance.add("M071190278");
instance.add("M071180132");
instance.add("M071190254");
instance.add("M071190194");
instance.add("M071190018");
instance.add("M071200147");
instance.add("M071200031");
instance.add("M071200145");
instance.add("M071200074");
instance.add("M071180083");
instance.add("M071180344");
instance.add("M071190111");
instance.add("M071190135");
instance.add("M071190276");
instance.add("M095180119");
instance.add("M071200166");
instance.add("M071200053");
instance.add("M071200015");
instance.add("M071190233");
instance.add("M095200120");
instance.add("M071190222");
instance.add("M071190128");
instance.add("M071190202");
instance.add("M095200153");
instance.add("M071190274");
instance.add("M071200131");
instance.add("M071190269");
instance.add("M071200188");
instance.add("M071180284");
instance.add("M071200212");
instance.add("M095180043");
instance.add("M071190299");
instance.add("M095200041");
instance.add("M095190229");
instance.add("M095200176");
instance.add("M071200063");
instance.add("M095200080");
instance.add("M095200063");
instance.add("M071190235");
instance.add("M071200149");
instance.add("M095200148");
instance.add("M095200143");
instance.add("M055200129");
instance.add("M055190066");
instance.add("M055200141");
instance.add("M055200178");
instance.add("M055200158");
instance.add("M055200187");
instance.add("M055200048");
instance.add("M055190127");
instance.add("M018200007");
instance.add("M018180019");
instance.add("M005200269");
instance.add("M149200070");
instance.add("M047200026");
instance.add("M149180005");
instance.add("M047200097");
instance.add("M104190081");
instance.add("M104200031");
instance.add("M152200069");
instance.add("M152200046");
instance.add("M152200065");
instance.add("M152200052");
instance.add("M077190186");
instance.add("M139190068");
instance.add("M139190035");
instance.add("M139190163");
instance.add("M169190001");
instance.add("M061190188");
instance.add("M015190161");
instance.add("M015200238");
instance.add("M015180140");
instance.add("M105200170");
instance.add("M105190272");
instance.add("M147190059");
instance.add("M147200012");
instance.add("M147200052");
instance.add("M094200022");
instance.add("M094180260");
instance.add("M142200052");
instance.add("M055200244");
instance.add("M032200032");
instance.add("M106200107");
instance.add("M032180205");
instance.add("M070200110");
instance.add("M066190125");
instance.add("M070200071");
instance.add("M070180192");
instance.add("M070200072");
instance.add("M070200034");
instance.add("M132200107");
instance.add("M107200167");
instance.add("M038200119");
instance.add("M068200047");
instance.add("M068190027");
instance.add("M068200004");
instance.add("M060200216");
instance.add("M040200096");
instance.add("M040190151");
instance.add("M040200119");
instance.add("M040180074");
instance.add("M040200113");
instance.add("M040200151");
instance.add("M054200018");
instance.add("M082180024");
instance.add("M082200033");
instance.add("M082200117");
instance.add("M082200005");
instance.add("M082200069");
instance.add("M081200066");
instance.add("M039200095");
instance.add("M039200062");
instance.add("M114180064");
instance.add("M114200105");
instance.add("M155200056");
instance.add("M058200045");
instance.add("M114190153");
instance.add("M053190001");
instance.add("M137200127");
instance.add("M137190157");
instance.add("M137190292");
instance.add("M137200096");
instance.add("M120180180");
instance.add("M154200118");
instance.add("M088180020");
instance.add("M088200063");
instance.add("M088190108");
instance.add("M057190018");
instance.add("M057180144");
instance.add("M119200052");
instance.add("M119180171");
instance.add("M106200057");
instance.add("M032190047");
instance.add("M163200008");
instance.add("M120180223");
instance.add("M120200201");
instance.add("M063200120");
instance.add("M063200081");
instance.add("M075200115");
instance.add("M132200054");
instance.add("M038180038");
instance.add("M132200033");
instance.add("M045180031");
instance.add("M112200001");
instance.add("M112190125");
instance.add("M112190064");
instance.add("M045190062");
instance.add("M112190166");
instance.add("M068200039");
instance.add("M022190026");
instance.add("M097200149");
instance.add("M097200104");
instance.add("M097200154");
instance.add("M097190233");
instance.add("M097200071");
instance.add("M097200046");
instance.add("M097200124");
instance.add("M097200148");
instance.add("M082190125");
instance.add("M036190199");
instance.add("M036200067");
instance.add("M036200182");
instance.add("M036180028");
instance.add("M048200063");
instance.add("M001200349");
instance.add("M117180040");
instance.add("M129200088");
instance.add("M129200014");
instance.add("M141200061");
instance.add("M048190360");
instance.add("M001180216");
instance.add("M048200199");
instance.add("M158200024");
instance.add("M082190246");
instance.add("M082190189");
instance.add("M082190092");
instance.add("M082190005");
instance.add("M036200090");
instance.add("M061190274");
instance.add("M061200179");
instance.add("M021200050");
instance.add("M021200036");
instance.add("M006190143");
instance.add("M105190051");
instance.add("M150200095");
instance.add("M150200071");
instance.add("M150200115");
instance.add("M150190065");
instance.add("M150190062");
instance.add("M018200124");
instance.add("M020180119");
instance.add("M134190185");
instance.add("M020200030");
instance.add("M020190149");
instance.add("M085200069");
instance.add("M085200062");
instance.add("M008200252");
instance.add("M008190155");
instance.add("M008190431");
instance.add("M008190422");
instance.add("M043180018");
instance.add("M008200175");
instance.add("M008200212");
instance.add("M041190188");
instance.add("M041190105");
instance.add("M041200099");
instance.add("M099190070");
instance.add("M099190006");
instance.add("M099190022");
instance.add("M099180112");
instance.add("M084180067");
instance.add("M084180066");
instance.add("M025190148");
instance.add("M058190127");
instance.add("M006190171");
instance.add("M074200109");
instance.add("M074190085");
instance.add("M135200078");
instance.add("M052200057");
instance.add("M139200058");
instance.add("M052200052");
instance.add("M052200046");
instance.add("M002200002");
instance.add("M0141X0410");
instance.add("M029200069");
instance.add("M108200057");
instance.add("M117190081");
instance.add("M117200030");
instance.add("M117200001");
instance.add("M117200042");
instance.add("M117200063");
instance.add("M117180066");
instance.add("M117200044");
instance.add("M031190082");
instance.add("M072180155");
instance.add("M072190038");
instance.add("M072200073");
instance.add("M072190106");
instance.add("M072180091");
instance.add("M031180058");
instance.add("M072210005");
instance.add("M031180040");
instance.add("M072180135");
instance.add("M072190104");
instance.add("M008180379");
instance.add("M094180275");
instance.add("M094200093");
instance.add("M094190177");
instance.add("M094190314");
instance.add("M061200022");
instance.add("M061190065");
instance.add("M0611X0658");
instance.add("M061200170");
instance.add("M129200067");
instance.add("M014200103");
instance.add("M042200059");
instance.add("M087190219");
instance.add("M087200086");
instance.add("M087200082");
instance.add("M087200003");
instance.add("M149200026");
instance.add("M047190039");
instance.add("M049190068");
instance.add("M140190080");
instance.add("M057200013");
instance.add("M018200111");
instance.add("M145200103");
instance.add("M069190237");
instance.add("M113200030");
instance.add("M160200027");
instance.add("M129190062");
instance.add("M049200002");
instance.add("M016180139");
instance.add("M101190074");
instance.add("M101200038");
instance.add("M102180088");
instance.add("M016180064");
instance.add("M049190118");
instance.add("M102200087");
instance.add("M049190204");
instance.add("M137200079");
instance.add("M074180041");
instance.add("M137190229");
instance.add("M074180098");
instance.add("M042200070");
instance.add("M087200127");
instance.add("M008190165");
instance.add("M013200087");
instance.add("M013190164");
instance.add("M013180130");
instance.add("M013200097");
instance.add("M013180087");
instance.add("M013190100");
instance.add("M013200110");
instance.add("M018180193");
instance.add("M107200162");
instance.add("M018190020");
instance.add("M005190081");
instance.add("M115180029");
instance.add("M091200263");
instance.add("M091190217");
instance.add("M091180051");
instance.add("M091180310");
instance.add("M115190125");
instance.add("M115190069");
instance.add("M091200158");
instance.add("M091190174");
instance.add("M075200100");
instance.add("M075200151");
instance.add("M075200096");
instance.add("M116180088");
instance.add("M159200060");
instance.add("M118190042");
instance.add("M159200083");
instance.add("M142190072");
instance.add("M159200038");
instance.add("M030190030");
instance.add("M016200051");
instance.add("M016180041");
instance.add("M049200227");
instance.add("M162200006");
instance.add("M123200023");
instance.add("M123200013");
instance.add("M063190126");
instance.add("M120180130");
instance.add("M120200236");
instance.add("M063200138");
instance.add("M093200128");
instance.add("M046190045");
instance.add("M029190051");
instance.add("M103200071");
instance.add("M138200051");
instance.add("M103200078");
instance.add("M029200072");
instance.add("M029200130");
instance.add("M102200067");
instance.add("M055200155");
instance.add("M055200157");
instance.add("M055190151");
instance.add("M055180225");
instance.add("M055180105");
instance.add("M157200012");
instance.add("M055190409");
instance.add("M160200047");
instance.add("M160200016");
instance.add("M131200031");
instance.add("M093190127");
instance.add("M093200046");
instance.add("M146190040");
instance.add("M054180144");
instance.add("M054200043");
instance.add("M054200070");
instance.add("M005200175");
instance.add("M069190014");
instance.add("M018180097");
instance.add("M088190163");
instance.add("M088200076");
instance.add("M057200116");
instance.add("M057180230");
instance.add("M029190050");
instance.add("M031190068");
instance.add("M031180124");
instance.add("M031200045");
instance.add("M047180419");
instance.add("M043190146");
instance.add("M056180017");
instance.add("M056200050");
instance.add("M009180033");
instance.add("M124190255");
instance.add("M124200098");
instance.add("M124200129");
instance.add("M011200008");
instance.add("M134180062");
instance.add("M085180059");
instance.add("M053190010");
instance.add("M018190093");
instance.add("M018200024");
instance.add("M039200179");
instance.add("M091200285");
instance.add("M091200236");
instance.add("M153200004");
instance.add("M040190110");
instance.add("M040200194");
instance.add("M054200094");
instance.add("M054190003");
instance.add("M009200059");
instance.add("M009190016");
instance.add("M009180026");
instance.add("M009200027");
instance.add("M009200002");
instance.add("M109200077");
instance.add("M109200066");
instance.add("M109190067");
instance.add("M109180041");
instance.add("M109200088");
instance.add("GCC1200002");
instance.add("M001200138");
instance.add("M035200158");
instance.add("M092200130");
instance.add("M092200012");
instance.add("M077190086");
instance.add("M077200109");
instance.add("M077200074");
instance.add("M077200091");
instance.add("M101190017");
instance.add("M077200140");
instance.add("M102190079");
instance.add("M016190192");
instance.add("M019200034");
instance.add("M096190048");
instance.add("M011190471");
instance.add("M088200032");
instance.add("M088200107");
instance.add("M088190045");
instance.add("M140190035");
instance.add("M140190131");
instance.add("M057190164");
instance.add("M057200072");
instance.add("M145200086");
instance.add("M069200070");
instance.add("M035190028");
instance.add("M092180303");
instance.add("M092190213");
instance.add("M035210001");
instance.add("M035200053");
instance.add("M035200143");
instance.add("M091190087");
instance.add("M091180227");
instance.add("M091180030");
instance.add("M091200303");
instance.add("M091200098");
instance.add("M087180090");
instance.add("M087200081");
instance.add("M087200061");
instance.add("M087200117");
instance.add("M032190087");
instance.add("M032190114");
instance.add("M032190061");
instance.add("M106200106");
instance.add("M032190340");
instance.add("M106190129");
instance.add("M097200155");
instance.add("M097200123");
instance.add("M097200144");
instance.add("M097180056");
instance.add("M097190114");
instance.add("M097200133");
instance.add("M097200183");
instance.add("M037190047");
instance.add("M083190045");
instance.add("M083200111");
instance.add("M083190090");
instance.add("M083190101");
instance.add("M083180145");
instance.add("M083190028");
instance.add("M083190116");
instance.add("M100190120");
instance.add("M125190077");
instance.add("M125190051");
instance.add("M100190017");
instance.add("M125190065");
instance.add("M107190034");
instance.add("M084190025");
instance.add("M084200101");
instance.add("M084190047");
instance.add("M102190058");
instance.add("M049200201");
instance.add("M102190093");
instance.add("M051190040");
instance.add("M136200085");
instance.add("M136190116");
instance.add("M110190192");
instance.add("M110200084");
instance.add("M110180070");
instance.add("M023190021");
instance.add("M136200063");
instance.add("M023190131");
instance.add("M023180048");
instance.add("M096200043");
instance.add("M003180028");
instance.add("M045200070");
instance.add("M066200152");
instance.add("M066200074");
instance.add("M066200081");
instance.add("M066180146");
instance.add("M026190309");
instance.add("M026200163");
instance.add("M026190106");
instance.add("M026190249");
instance.add("M026190349");
instance.add("M026200005");
instance.add("M026190157");
instance.add("M026190223");
instance.add("M097200005");
instance.add("M097200173");
instance.add("M097200014");
instance.add("M097200177");
instance.add("M018200142");
instance.add("M069190188");
instance.add("M069190167");
instance.add("M069170263");
instance.add("M069200050");
instance.add("M069180159");
instance.add("M081200043");
instance.add("M081200025");
instance.add("M004190247");
instance.add("M004200068");
instance.add("M004190246");
instance.add("M037180131");
instance.add("M111200019");
instance.add("M050200024");
instance.add("M001200131");
instance.add("M164200026");
instance.add("M111200099");
instance.add("M126190018");
instance.add("M002200043");
instance.add("M002200033");
instance.add("M050190222");
instance.add("M126200031");
instance.add("M001190152");
instance.add("M001200399");
instance.add("M130190080");
instance.add("M130200109");
instance.add("M130200022");
instance.add("M133200139");
instance.add("M051190076");
instance.add("M051190095");
instance.add("M051200027");
instance.add("M051190107");
instance.add("M105200199");
instance.add("M015200118");
instance.add("M015180229");
instance.add("M039200166");
instance.add("M044200025");
instance.add("M039180127");
instance.add("M039200010");
instance.add("M032190227");
instance.add("M032200096");
instance.add("M106200170");
instance.add("M009190049");
instance.add("M041200129");
instance.add("M041200017");
instance.add("M041180040");
instance.add("M041190197");
instance.add("M041190031");
instance.add("M041190005");
instance.add("M041180207");
instance.add("M041200086");
instance.add("M048190410");
instance.add("M048200106");
instance.add("M098200063");
instance.add("M098200138");
instance.add("M133200074");
instance.add("M133200070");
instance.add("M130200034");
instance.add("M130200055");
instance.add("M096200021");
instance.add("M104200013");
instance.add("M024200045");
instance.add("M104200027");
instance.add("M146200169");
instance.add("M093190352");
instance.add("M093190359");
instance.add("M093180323");
instance.add("M167200039");
instance.add("M093180419");
instance.add("M167210001");
instance.add("M167210004");
instance.add("M007190199");
instance.add("M007200115");
instance.add("M093200107");
instance.add("M011200157");
instance.add("M011190175");
instance.add("M011180146");
instance.add("M011190291");
instance.add("M011200146");
instance.add("M011200145");
instance.add("M011190215");
instance.add("M011200164");
instance.add("M114180076");
instance.add("M114180148");
instance.add("M114190034");
instance.add("M127200022");
instance.add("M127200057");
instance.add("M127180056");
instance.add("M127190072");
instance.add("M127200049");
instance.add("M127200066");
instance.add("M078180108");
instance.add("M078200075");
instance.add("M078200041");
instance.add("M134190030");
instance.add("M085180087");
instance.add("M060190027");
instance.add("M134190101");
instance.add("M134190079");
instance.add("M134200084");
instance.add("M020200086");
instance.add("M086200088");
instance.add("M122200049");
instance.add("M008190312");
instance.add("M043190192");
instance.add("M008180120");
instance.add("M043180183");
instance.add("M043190011");
instance.add("M008190375");
instance.add("M141200042");
instance.add("M060190319");
instance.add("M020200128");
instance.add("M060180073");
instance.add("M020200016");
instance.add("M020200135");
instance.add("M059190069");
instance.add("M001200060");
instance.add("M158200073");
instance.add("M059180087");
instance.add("M048200221");
instance.add("M064190041");
instance.add("M064180203");
instance.add("M064190259");
instance.add("M064200149");
instance.add("M166200026");
instance.add("M064180261");
instance.add("M038180176");
instance.add("M166200037");
instance.add("M064190284");
instance.add("M064190322");
instance.add("M035190169");
instance.add("M092190244");
instance.add("M092190342");
instance.add("M035200144");
instance.add("M050190107");
instance.add("M035190391");
instance.add("M092180042");
instance.add("M035190195");
instance.add("M035180167");
instance.add("M035190152");
instance.add("M048200023");
instance.add("M005180088");
instance.add("M156200042");
instance.add("M048200181");
instance.add("M048190269");
instance.add("M001200312");
instance.add("M166200009");
instance.add("M048200021");
instance.add("M166200018");
instance.add("M108200011");
instance.add("M108190030");
instance.add("M062200045");
instance.add("M006180107");
instance.add("M128200022");
instance.add("M059200045");
instance.add("M025190040");
instance.add("M025190056");
instance.add("M025190017");
instance.add("M025180137");
instance.add("M058200079");
instance.add("M076200071");
instance.add("M076200073");
instance.add("M098190048");
instance.add("M098200119");
instance.add("M098190068");
instance.add("M119180108");
instance.add("M119200002");
instance.add("M119180110");
instance.add("M119180082");
instance.add("M119200054");
instance.add("M119180030");
instance.add("M119190075");
instance.add("M119200014");
instance.add("M001190280");
instance.add("M028190028");
instance.add("M050180296");
instance.add("M001200306");
instance.add("M086200051");
instance.add("M032180297");
instance.add("M032190197");
instance.add("M032200191");
instance.add("M106200063");
instance.add("M032180338");
instance.add("M106180264");
instance.add("M032200106");
instance.add("M032200184");
instance.add("M032180073");
instance.add("M085200095");
instance.add("M134200050");
instance.add("M060200009");
instance.add("M060200071");
instance.add("M001200324");
instance.add("M027200083");
instance.add("M027180152");
instance.add("M027200104");
instance.add("M016180093");
instance.add("M127200044");
        
        return instance;
    }

}

