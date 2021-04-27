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
                            ", dTransact = '2021-04-26'" + 
                            ", dCollDate = '2021-04-26'" + 
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
        
        instance.add("GCC1200023");
instance.add("GCC1200025");
instance.add("GCC1200028");
instance.add("M001190233");
instance.add("M001200092");
instance.add("M001200127");
instance.add("M001200137");
instance.add("M001200147");
instance.add("M001200172");
instance.add("M001200202");
instance.add("M001200333");
instance.add("M001200350");
instance.add("M001200382");
instance.add("M001200419");
instance.add("M002200003");
instance.add("M002200015");
instance.add("M003200040");
instance.add("M004180149");
instance.add("M004190020");
instance.add("M004200011");
instance.add("M004200122");
instance.add("M004210004");
instance.add("M005190239");
instance.add("M005200119");
instance.add("M005200215");
instance.add("M005200234");
instance.add("M005200243");
instance.add("M006180110");
instance.add("M006190171");
instance.add("M006200034");
instance.add("M006210006");
instance.add("M007180165");
instance.add("M007180217");
instance.add("M007190113");
instance.add("M007190199");
instance.add("M007190227");
instance.add("M007200013");
instance.add("M007200104");
instance.add("M007200174");
instance.add("M007200225");
instance.add("M008190312");
instance.add("M008190384");
instance.add("M008190401");
instance.add("M008200003");
instance.add("M008200032");
instance.add("M008210019");
instance.add("M009190057");
instance.add("M009200002");
instance.add("M009200027");
instance.add("M009200052");
instance.add("M009200055");
instance.add("M009200069");
instance.add("M011200090");
instance.add("M011200173");
instance.add("M011210003");
instance.add("M012190169");
instance.add("M012200077");
instance.add("M012210006");
instance.add("M013190116");
instance.add("M013190173");
instance.add("M014190135");
instance.add("M014200092");
instance.add("M015180140");
instance.add("M015180228");
instance.add("M015190096");
instance.add("M015200077");
instance.add("M015200089");
instance.add("M015200107");
instance.add("M015200114");
instance.add("M015200120");
instance.add("M015200123");
instance.add("M015200167");
instance.add("M015200169");
instance.add("M015200173");
instance.add("M015200195");
instance.add("M015200199");
instance.add("M015210009");
instance.add("M016180112");
instance.add("M016190053");
instance.add("M016190199");
instance.add("M016200099");
instance.add("M016200100");
instance.add("M016200101");
instance.add("M016210006");
instance.add("M016210020");
instance.add("M017200082");
instance.add("M018190231");
instance.add("M018200007");
instance.add("M018200069");
instance.add("M018200071");
instance.add("M018200072");
instance.add("M018200129");
instance.add("M019180067");
instance.add("M019190163");
instance.add("M019200061");
instance.add("M019200062");
instance.add("M021200028");
instance.add("M021200057");
instance.add("M021200072");
instance.add("M022180115");
instance.add("M022180184");
instance.add("M022200037");
instance.add("M022200041");
instance.add("M022200050");
instance.add("M023200055");
instance.add("M023200102");
instance.add("M023200109");
instance.add("M023200123");
instance.add("M023210005");
instance.add("M024180086");
instance.add("M024190004");
instance.add("M024200054");
instance.add("M025180137");
instance.add("M025200007");
instance.add("M025200042");
instance.add("M025200049");
instance.add("M025200056");
instance.add("M025200064");
instance.add("M026190153");
instance.add("M026200076");
instance.add("M026200119");
instance.add("M026200129");
instance.add("M026200177");
instance.add("M026200213");
instance.add("M026200262");
instance.add("M026200265");
instance.add("M027190112");
instance.add("M027200105");
instance.add("M028190040");
instance.add("M029180084");
instance.add("M029190067");
instance.add("M029200055");
instance.add("M029200056");
instance.add("M029200079");
instance.add("M030200034");
instance.add("M031200002");
instance.add("M031200034");
instance.add("M031200056");
instance.add("M031210002");
instance.add("M032180338");
instance.add("M032190197");
instance.add("M032190293");
instance.add("M032200052");
instance.add("M032200071");
instance.add("M032200105");
instance.add("M032200138");
instance.add("M032200159");
instance.add("M032200186");
instance.add("M032210012");
instance.add("M034200021");
instance.add("M035180206");
instance.add("M035180238");
instance.add("M035180316");
instance.add("M035190169");
instance.add("M035190195");
instance.add("M035190236");
instance.add("M035190238");
instance.add("M035200054");
instance.add("M035200135");
instance.add("M035200158");
instance.add("M035200159");
instance.add("M035200213");
instance.add("M036200079");
instance.add("M036200098");
instance.add("M036200110");
instance.add("M036200127");
instance.add("M036200170");
instance.add("M036200172");
instance.add("M037190028");
instance.add("M037200051");
instance.add("M038180185");
instance.add("M038190143");
instance.add("M038190163");
instance.add("M038200018");
instance.add("M038200054");
instance.add("M039200081");
instance.add("M039200108");
instance.add("M039200131");
instance.add("M039200165");
instance.add("M039200189");
instance.add("M039200193");
instance.add("M040190305");
instance.add("M041180117");
instance.add("M041190001");
instance.add("M041190199");
instance.add("M041200003");
instance.add("M041200009");
instance.add("M041200030");
instance.add("M041200068");
instance.add("M041200136");
instance.add("M042190067");
instance.add("M042190167");
instance.add("M042200059");
instance.add("M042200065");
instance.add("M042200092");
instance.add("M043190164");
instance.add("M043190181");
instance.add("M043200006");
instance.add("M043200041");
instance.add("M044190053");
instance.add("M044190129");
instance.add("M044200025");
instance.add("M044200040");
instance.add("M045190068");
instance.add("M045200057");
instance.add("M045200091");
instance.add("M046200057");
instance.add("M046200086");
instance.add("M046200104");
instance.add("M046200126");
instance.add("M046200140");
instance.add("M046200148");
instance.add("M047180255");
instance.add("M047180263");
instance.add("M047190153");
instance.add("M047190195");
instance.add("M047190262");
instance.add("M047200084");
instance.add("M047200086");
instance.add("M047200107");
instance.add("M047200125");
instance.add("M047200159");
instance.add("M047210003");
instance.add("M048190158");
instance.add("M048200106");
instance.add("M048200139");
instance.add("M048200170");
instance.add("M048210006");
instance.add("M048210016");
instance.add("M049180252");
instance.add("M049180306");
instance.add("M049180325");
instance.add("M049190063");
instance.add("M049190214");
instance.add("M049200002");
instance.add("M049200024");
instance.add("M049200115");
instance.add("M049200123");
instance.add("M049200128");
instance.add("M049200130");
instance.add("M050180154");
instance.add("M050200066");
instance.add("M051180123");
instance.add("M051200023");
instance.add("M051200031");
instance.add("M051200034");
instance.add("M052190001");
instance.add("M052200031");
instance.add("M053190102");
instance.add("M054190067");
instance.add("M054200028");
instance.add("M054200056");
instance.add("M054200084");
instance.add("M054200085");
instance.add("M054200104");
instance.add("M054200105");
instance.add("M054200113");
instance.add("M054200124");
instance.add("M054200127");
instance.add("M055180105");
instance.add("M055190059");
instance.add("M055190282");
instance.add("M055190323");
instance.add("M055190409");
instance.add("M055200113");
instance.add("M055200122");
instance.add("M055200131");
instance.add("M055200175");
instance.add("M055200178");
instance.add("M055200248");
instance.add("M056200105");
instance.add("M057180173");
instance.add("M057190090");
instance.add("M057200060");
instance.add("M057200078");
instance.add("M057200114");
instance.add("M059190198");
instance.add("M059200064");
instance.add("M060190323");
instance.add("M060200080");
instance.add("M060200082");
instance.add("M060200085");
instance.add("M060200101");
instance.add("M060200103");
instance.add("M060200109");
instance.add("M060200111");
instance.add("M060200158");
instance.add("M060200180");
instance.add("M060200182");
instance.add("M060200205");
instance.add("M061180303");
instance.add("M061190065");
instance.add("M061190116");
instance.add("M061190126");
instance.add("M061190189");
instance.add("M061200091");
instance.add("M061200117");
instance.add("M061200148");
instance.add("M062180119");
instance.add("M062200034");
instance.add("M063190277");
instance.add("M063190331");
instance.add("M063200068");
instance.add("M064180145");
instance.add("M064190249");
instance.add("M064200073");
instance.add("M064200140");
instance.add("M064200169");
instance.add("M066180128");
instance.add("M066190152");
instance.add("M066200049");
instance.add("M066200050");
instance.add("M066200157");
instance.add("M066210001");
instance.add("M066210015");
instance.add("M068190052");
instance.add("M068200049");
instance.add("M068200062");
instance.add("M069180159");
instance.add("M069180186");
instance.add("M069180228");
instance.add("M069200049");
instance.add("M069200077");
instance.add("M069200106");
instance.add("M069200118");
instance.add("M069200120");
instance.add("M069200122");
instance.add("M070200115");
instance.add("M070200129");
instance.add("M070210003");
instance.add("M070210004");
instance.add("M071180104");
instance.add("M071180142");
instance.add("M071180194");
instance.add("M071180334");
instance.add("M071180338");
instance.add("M071190030");
instance.add("M071190235");
instance.add("M071190284");
instance.add("M071200031");
instance.add("M071200048");
instance.add("M071200053");
instance.add("M071200156");
instance.add("M071200176");
instance.add("M071200197");
instance.add("M071210005");
instance.add("M072200039");
instance.add("M072200050");
instance.add("M072200051");
instance.add("M072200077");
instance.add("M074180139");
instance.add("M074190027");
instance.add("M074190133");
instance.add("M074200085");
instance.add("M074200086");
instance.add("M074200112");
instance.add("M075180175");
instance.add("M076190077");
instance.add("M076200095");
instance.add("M077180233");
instance.add("M077200082");
instance.add("M077200091");
instance.add("M077200100");
instance.add("M077200112");
instance.add("M078180108");
instance.add("M078210002");
instance.add("M079180054");
instance.add("M079190061");
instance.add("M079200095");
instance.add("M081190049");
instance.add("M081190056");
instance.add("M081200044");
instance.add("M081200083");
instance.add("M082190130");
instance.add("M082200010");
instance.add("M082200069");
instance.add("M082200168");
instance.add("M083190062");
instance.add("M083190084");
instance.add("M083190090");
instance.add("M083190100");
instance.add("M083190101");
instance.add("M083190119");
instance.add("M083190135");
instance.add("M083200043");
instance.add("M083200062");
instance.add("M083200111");
instance.add("M084190097");
instance.add("M084190145");
instance.add("M084200118");
instance.add("M084210006");
instance.add("M085200033");
instance.add("M085200064");
instance.add("M085200086");
instance.add("M085200093");
instance.add("M085200106");
instance.add("M085200107");
instance.add("M085200110");
instance.add("M085210005");
instance.add("M087190081");
instance.add("M087200054");
instance.add("M087200072");
instance.add("M087200123");
instance.add("M088180176");
instance.add("M088190045");
instance.add("M088190150");
instance.add("M088200010");
instance.add("M088200117");
instance.add("M088200132");
instance.add("M088210010");
instance.add("M089180129");
instance.add("M089200013");
instance.add("M089200033");
instance.add("M089200047");
instance.add("M089210013");
instance.add("M090180075");
instance.add("M090200023");
instance.add("M090200057");
instance.add("M091190192");
instance.add("M091190301");
instance.add("M091200082");
instance.add("M091200134");
instance.add("M091200166");
instance.add("M091200172");
instance.add("M091200176");
instance.add("M091200179");
instance.add("M091200223");
instance.add("M091200293");
instance.add("M091200333");
instance.add("M092180145");
instance.add("M092190228");
instance.add("M092190339");
instance.add("M092200045");
instance.add("M092200069");
instance.add("M092200073");
instance.add("M092200120");
instance.add("M092200127");
instance.add("M092200148");
instance.add("M092210005");
instance.add("M093180189");
instance.add("M093180422");
instance.add("M093200181");
instance.add("M094180218");
instance.add("M094180275");
instance.add("M094190177");
instance.add("M094190245");
instance.add("M094190300");
instance.add("M094200062");
instance.add("M094200108");
instance.add("M095190162");
instance.add("M095190164");
instance.add("M095200088");
instance.add("M095200094");
instance.add("M095200098");
instance.add("M096200102");
instance.add("M097180135");
instance.add("M097180176");
instance.add("M097190114");
instance.add("M097200111");
instance.add("M097200143");
instance.add("M097200161");
instance.add("M097200180");
instance.add("M098190199");
instance.add("M098190216");
instance.add("M098200065");
instance.add("M098200082");
instance.add("M098200086");
instance.add("M098200106");
instance.add("M098200108");
instance.add("M098200128");
instance.add("M099180060");
instance.add("M099180064");
instance.add("M099180120");
instance.add("M099190003");
instance.add("M099190023");
instance.add("M099190047");
instance.add("M099190051");
instance.add("M099190072");
instance.add("M099190085");
instance.add("M099200013");
instance.add("M099200017");
instance.add("M099200040");
instance.add("M099200049");
instance.add("M099200054");
instance.add("M100180026");
instance.add("M100180114");
instance.add("M100190053");
instance.add("M100190080");
instance.add("M100200033");
instance.add("M100200044");
instance.add("M100200046");
instance.add("M100200058");
instance.add("M100200059");
instance.add("M100200062");
instance.add("M100200068");
instance.add("M100200071");
instance.add("M101180074");
instance.add("M101200063");
instance.add("M101200078");
instance.add("M101200081");
instance.add("M101200082");
instance.add("M101200083");
instance.add("M102190074");
instance.add("M102200055");
instance.add("M102200081");
instance.add("M103200049");
instance.add("M103200072");
instance.add("M104200046");
instance.add("M104200074");
instance.add("M105190090");
instance.add("M105200069");
instance.add("M105200111");
instance.add("M105200137");
instance.add("M105200186");
instance.add("M105200192");
instance.add("M105200226");
instance.add("M106180232");
instance.add("M106190357");
instance.add("M106200006");
instance.add("M106200070");
instance.add("M106200145");
instance.add("M106200163");
instance.add("M106200194");
instance.add("M106200215");
instance.add("M107190038");
instance.add("M107190096");
instance.add("M107190152");
instance.add("M107200094");
instance.add("M107200099");
instance.add("M107200123");
instance.add("M107200125");
instance.add("M107200154");
instance.add("M108190030");
instance.add("M108200022");
instance.add("M108200051");
instance.add("M108200053");
instance.add("M109190027");
instance.add("M109190062");
instance.add("M109190108");
instance.add("M109210003");
instance.add("M110180228");
instance.add("M110190153");
instance.add("M110200084");
instance.add("M110200085");
instance.add("M110200115");
instance.add("M112190151");
instance.add("M112200023");
instance.add("M112200027");
instance.add("M112200055");
instance.add("M112200090");
instance.add("M112210004");
instance.add("M113180084");
instance.add("M113180118");
instance.add("M113200003");
instance.add("M114200058");
instance.add("M114200059");
instance.add("M114200060");
instance.add("M114200119");
instance.add("M115200049");
instance.add("M115200100");
instance.add("M116200057");
instance.add("M116200069");
instance.add("M116200070");
instance.add("M116200071");
instance.add("M116200086");
instance.add("M116200134");
instance.add("M117180032");
instance.add("M117180075");
instance.add("M117190094");
instance.add("M117200027");
instance.add("M117200049");
instance.add("M117200051");
instance.add("M118200021");
instance.add("M118200040");
instance.add("M119180093");
instance.add("M119180171");
instance.add("M119190039");
instance.add("M119200024");
instance.add("M119200025");
instance.add("M119200026");
instance.add("M119200027");
instance.add("M119200041");
instance.add("M119200043");
instance.add("M119200046");
instance.add("M119200048");
instance.add("M120180298");
instance.add("M120200096");
instance.add("M120200109");
instance.add("M120200125");
instance.add("M120200126");
instance.add("M120200145");
instance.add("M120210040");
instance.add("M121180040");
instance.add("M121180045");
instance.add("M122190040");
instance.add("M122200066");
instance.add("M124190240");
instance.add("M124200036");
instance.add("M124200107");
instance.add("M124210002");
instance.add("M125190080");
instance.add("M125190159");
instance.add("M125200032");
instance.add("M125200056");
instance.add("M125200057");
instance.add("M125200065");
instance.add("M125200067");
instance.add("M125200071");
instance.add("M125200074");
instance.add("M125200075");
instance.add("M125200100");
instance.add("M125200114");
instance.add("M126180050");
instance.add("M126200021");
instance.add("M127190066");
instance.add("M127200060");
instance.add("M127200063");
instance.add("M129200014");
instance.add("M129200023");
instance.add("M129200058");
instance.add("M129200065");
instance.add("M130190084");
instance.add("M130190099");
instance.add("M130200039");
instance.add("M130200051");
instance.add("M130200067");
instance.add("M130200095");
instance.add("M130200113");
instance.add("M130200134");
instance.add("M131190043");
instance.add("M131200033");
instance.add("M132180089");
instance.add("M132180090");
instance.add("M132190251");
instance.add("M132200109");
instance.add("M132200154");
instance.add("M133180129");
instance.add("M134200051");
instance.add("M134200055");
instance.add("M134200106");
instance.add("M135180033");
instance.add("M135200112");
instance.add("M135200121");
instance.add("M136200052");
instance.add("M137190206");
instance.add("M137200090");
instance.add("M137200091");
instance.add("M137200092");
instance.add("M137200123");
instance.add("M137210014");
instance.add("M139190087");
instance.add("M139190116");
instance.add("M139200035");
instance.add("M139200066");
instance.add("M139200100");
instance.add("M140190058");
instance.add("M140190060");
instance.add("M140190064");
instance.add("M140190127");
instance.add("M140200047");
instance.add("M141190060");
instance.add("M141190075");
instance.add("M141190101");
instance.add("M141190110");
instance.add("M141200013");
instance.add("M141200046");
instance.add("M141200048");
instance.add("M142190042");
instance.add("M142200030");
instance.add("M143200031");
instance.add("M144200014");
instance.add("M144200028");
instance.add("M145190058");
instance.add("M145200067");
instance.add("M145200069");
instance.add("M145200108");
instance.add("M148200033");
instance.add("M148200051");
instance.add("M148210017");
instance.add("M149190029");
instance.add("M149200089");
instance.add("M150190020");
instance.add("M150200052");
instance.add("M150200075");
instance.add("M150200083");
instance.add("M150200103");
instance.add("M150200127");
instance.add("M151200054");
instance.add("M151200095");
instance.add("M152200031");
instance.add("M152200049");
instance.add("M153200025");
instance.add("M153200036");
instance.add("M153200057");
instance.add("M154190003");
instance.add("M154200080");
instance.add("M154200106");
instance.add("M154200131");
instance.add("M156200062");
instance.add("M157200039");
instance.add("M157200055");
instance.add("M158200006");
instance.add("M158200072");
instance.add("M159200006");
instance.add("M159200010");
instance.add("M159200046");
instance.add("M159200055");
instance.add("M160200021");
instance.add("M160200023");
instance.add("M161200031");
instance.add("M161200032");
instance.add("M161200043");
instance.add("M161200055");
instance.add("M162200006");
instance.add("M162200014");
instance.add("M162200033");
instance.add("M163200030");
instance.add("M165200014");
instance.add("M165200020");     
        return instance;
    }

}

