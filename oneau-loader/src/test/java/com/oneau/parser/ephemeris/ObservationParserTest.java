package com.oneau.parser.ephemeris;
/**
 * User: ebridges
 * Date: Jul 23, 2010
 */

import static com.oneau.parser.ephemeris.util.AssertionUtil.assertArraysEqual;
import com.oneau.core.util.HeavenlyBody;
import com.oneau.core.util.Utility;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class ObservationParserTest {
    private Header header;
    ObservationParser underTest;


    @Before
    public void setUp() throws IOException {
        HeaderParser headerParser = new HeaderParser(HeaderParser.HEADER_405);
        this.header = headerParser.readHeader();
        this.underTest = new ObservationParser(header, "filename", 1, 1018);
        assertNotNull(this.underTest);
    }

    @Test
    public void testParseObservation() throws Exception {
        Observation o = this.underTest.parseObservation(new BufferedReader(new StringReader(ONE_OBSERVATION)));
        assertNotNull(o);

        List<Double> expectedResults = parseExpectedResults();
        List<Double> actualResults = extractActualResults(o);

        //System.out.println("Expect "+ getExpectedCount() + " coefficients.");
        assertArraysEqual(expectedResults, actualResults);
    }

    private List<Double> extractActualResults(Observation o) {
        List<Double> list = new ArrayList<Double>();
        list.add(o.getBeginEndDates().getLeft());
        list.add(o.getBeginEndDates().getRight());
        for(HeavenlyBody h : HeavenlyBody.orderedByIndex()) {
            list.addAll( o.getCoefficients().get(h) );
        }
        return list;
    }

    private List<Double> parseExpectedResults() throws IOException {
        BufferedReader r = new BufferedReader(new StringReader(ONE_OBSERVATION));
        List<Double> list = new ArrayList<Double>();
        String line;
        while( (line = r.readLine()) != null ){
            String[] fields = line.trim().split("\\s+");
            for(String f : fields) {
                Double coeff = Utility.parseCoefficient(f);
                if(coeff != 0.00)
                    list.add(coeff);
            }
        }
        return list;
    }

    private Integer getExpectedCount() {
        Integer count = 2; // include begin & end dates
        for(HeavenlyBody h : HeavenlyBody.values()) {
            CoefficientInfo ci = this.header.getCoeffInfo().get(h);
//            System.out.println(String.format("sets[%d] * cnt[%d]",ci.getCoeffSets(),ci.getCoeffCount()));
            count += ci.getCoeffSets() * ci.getCoeffCount() * h.getDimensions();
        }
        return count;
    }

    private static final String ONE_OBSERVATION =
            "  0.230542450000000000D+07  0.230545650000000000D+07 -0.170607698480945826D+08  \n" +
            "  0.128001717053831909D+08  0.213151978085375478D+06 -0.252343651776121515D+05  \n" +
            " -0.496086357427998337D+02 -0.121772822425335878D+02 -0.151579801424952587D+00  \n" +
            "  0.308966374346020560D-02 -0.210339063436067983D-03  0.204377117215395266D-04  \n" +
            " -0.176314287322559684D-06  0.323392935234662489D-07  0.299139439573599497D-10  \n" +
            "  0.255553534093704826D-10 -0.599595375367442667D+08 -0.264142157315789396D+07  \n" +
            "  0.700456550696964492D+06  0.460596267362614253D+04 -0.270497527986098817D+03  \n" +
            "  0.320758010460061671D+01 -0.545777407154330207D+00  0.107554763885226888D-02  \n" +
            " -0.548437017394245801D-03 -0.270107513793348174D-05 -0.179162844597980952D-06  \n" +
            " -0.782394097409358879D-08  0.592920194359201270D-09 -0.117640678929356861D-10  \n" +
            " -0.300778109260871150D+08 -0.275141568567689089D+07  0.351335210401644988D+06  \n" +
            "  0.510302857976135056D+04 -0.139105849981671270D+03  0.298797723799145221D+01  \n" +
            " -0.275272406981845963D+00  0.249835915564870293D-03 -0.270530281998029745D-03  \n" +
            " -0.358387076234256440D-05 -0.770945495549085238D-07 -0.756471952946983646D-08  \n" +
            "  0.313179179056808305D-09 -0.895542902163799648D-11  0.926342185474343039D+07  \n" +
            "  0.132609716099666711D+08 -0.103033578631496479D+06 -0.281496033264454709D+05  \n" +
            " -0.323517591309785587D+03 -0.148340018448585589D+02 -0.412273353941478432D-01  \n" +
            "  0.979554209240933969D-02  0.784806305878538949D-03  0.477267767786177026D-04  \n" +
            "  0.182889082554738153D-05  0.717212411835052658D-07  0.153925425974638000D-08  \n" +
            "  0.775054090646129631D-11 -0.595153940221562758D+08  0.310989362945293216D+07  \n" +
            "  0.729510432663550950D+06  0.523729184368874883D+02 -0.345731094072767064D+03  \n" +
            " -0.116597048342922065D+02 -0.787286125924238522D+00 -0.195761975978856959D-01  \n" +
            " -0.776814425275206780D-03 -0.716520515025944097D-05  0.318239347114103262D-06  \n" +
            "  0.474164538208950537D-07  0.290949944186087299D-08  0.136388354761273110D-09  \n" +
            " -0.326012676999638826D+08  0.268518297600529855D+06  0.399986895617230562D+06  \n" +
            "  0.297940128854886734D+04 -0.150522994757818196D+03 -0.466498036799768023D+01  \n" +
            " -0.415684787263461919D+00 -0.114707138033521908D-01 -0.496706880172998909D-03  \n" +
            " -0.882664991651377263D-05 -0.219802728216952202D-07  0.177761997780350698D-07  \n" +
            "  0.139079302374893900D-08  0.719488667020590362D-10  0.338146701935544983D+08  \n" +
            "  0.109753882254667189D+08 -0.481416453495319001D+06 -0.355821313043117989D+05  \n" +
            " -0.584079006677790858D+03 -0.742818541953273304D+01  0.984644401417453907D+00  \n" +
            "  0.836750961045769764D-01  0.470401336391276879D-02  0.191792664935857009D-03  \n" +
            "  0.452495987775628768D-05 -0.996104700444395844D-07 -0.208905281853290420D-07  \n" +
            " -0.156822304542433684D-08 -0.475404864919803068D+08  0.882892427364757098D+07  \n" +
            "  0.685294272586339968D+06 -0.860937811522893753D+04 -0.826971385742292796D+03  \n" +
            " -0.399253450109567467D+02 -0.166293623712252270D+01 -0.381104823615167759D-01  \n" +
            "  0.345437923898942953D-03  0.128125983522881149D-03  0.978787021588623445D-05  \n" +
            "  0.520990849100528535D-06  0.194989595989078157D-07  0.315737518594749286D-09  \n" +
            " -0.287873270779174492D+08  0.355918471218832163D+07  0.416071202316991519D+06  \n" +
            " -0.862246681012765634D+03 -0.379939185654914752D+03 -0.205208425203063669D+02  \n" +
            " -0.990394849439789504D+00 -0.291047120688727491D-01 -0.308923580520412838D-03  \n" +
            "  0.482443863952911845D-04  0.474727104481620333D-05  0.288386229528268280D-06  \n" +
            "  0.125928023809641163D-07  0.332868492244883038D-09  0.504509921476390213D+08  \n" +
            "  0.525980185363369063D+07 -0.962110163873897283D+06 -0.436405628649136925D+05  \n" +
            " -0.189305419649445042D+03  0.681700520132000349D+02  0.650960585427089899D+01  \n" +
            "  0.334952191847600778D+00  0.762187767832398378D-02 -0.521310232588282165D-03  \n" +
            " -0.774199451364589766D-04 -0.527903074223295662D-05 -0.193260642862096658D-06  \n" +
            "  0.301439329635034740D-08 -0.249359668249540143D+08  0.135969078456664737D+08  \n" +
            "  0.468473214075945434D+06 -0.306458709336182401D+05 -0.207218889326457111D+04  \n" +
            " -0.830244006955221749D+02 -0.100782893761720249D+01  0.181576920682604087D+00  \n" +
            "  0.194434381780057806D-01  0.112117631559752054D-02  0.306373490578978322D-04  \n" +
            " -0.149631107291253412D-05 -0.267434970887964791D-06 -0.197231007787938913D-07  \n" +
            " -0.184727614397923797D+08  0.670211157372073829D+07  0.350800066291567462D+06  \n" +
            " -0.117735292343291094D+05 -0.108563894357671825D+04 -0.514400476422504553D+02  \n" +
            " -0.122018916601997796D+01  0.617496870519861490D-01  0.957368898896754188D-02  \n" +
            "  0.652792507407996825D-03  0.244620407731613845D-04 -0.244763099614712504D-06  \n" +
            " -0.122410085554784967D-06 -0.108380727621856360D-07  0.772003460765573313D+06  \n" +
            " -0.241326974500023015D+08  0.356332189635468285D+04  0.512856773268754114D+05  \n" +
            "  0.357437790353455682D+02 -0.341396542688790703D+02 -0.849392853691370853D-01  \n" +
            "  0.126857538705209428D-01  0.901630167819489559D-04 -0.471198596284593229D-05  \n" +
            "  0.965151111163495481D+08 -0.724590056332208798D+06 -0.124271066938367137D+07  \n" +
            "  0.915232602428515747D+03  0.133966429031899452D+04  0.103366036965390573D+01  \n" +
            " -0.629559169691278120D+00 -0.239849663444947599D-02  0.199777427933402169D-03  \n" +
            "  0.208046542041799822D-05  0.432826336707901508D+08  0.120967215358307678D+07  \n" +
            " -0.557647286541255191D+06 -0.285098646415370877D+04  0.598642580953457809D+03  \n" +
            "  0.263612652317010632D+01 -0.277055397313153318D+00 -0.189733443970723230D-02  \n" +
            "  0.846227187148405213D-04  0.140720604859940029D-05 -0.455434519498575628D+08  \n" +
            " -0.216847516589932777D+08  0.599871526224707603D+06  0.464090582888004501D+05  \n" +
            " -0.637656084236014067D+03 -0.317490185285783930D+02  0.284854622129396440D+00  \n" +
            "  0.128430411045268329D-01 -0.790374985751389876D-04 -0.473343055571384745D-05  \n" +
            "  0.854138804733617306D+08 -0.102617756037089396D+08 -0.110512638281793613D+07  \n" +
            "  0.216853107087055723D+05  0.120781119153901136D+04 -0.140971725624372617D+02  \n" +
            " -0.597883134194599042D+00  0.482273547544272239D-02  0.227248592639816172D-03  \n" +
            " -0.146040121146205062D-05  0.412485243650726750D+08 -0.322392839821505919D+07  \n" +
            " -0.533854775483650854D+06  0.677577949204093602D+04  0.582325322021712850D+03  \n" +
            " -0.430512306563697855D+01 -0.286301886683908491D+00  0.135340150213734273D-02  \n" +
            "  0.106383446166274676D-03 -0.368197048603478960D-06  0.329395098561470397D+06  \n" +
            " -0.208721530670858100D+08  0.354336506866158516D+04  0.173378181264679697D+05  \n" +
            "  0.449774537401431740D+01 -0.494636235508965516D+01 -0.491656573468838252D-02  \n" +
            "  0.712163732835852147D-03 -0.248452924241622504D-04  0.776897104001528010D-05  \n" +
            "  0.194068033335622566D-05 -0.100785756978158162D-06 -0.103000574693919698D-06  \n" +
            "  0.133810256906746045D+09 -0.148369471767246025D+06 -0.670432866483965772D+06  \n" +
            " -0.267298656516630189D+02  0.292048769946606228D+03  0.168649790036561220D+00  \n" +
            " -0.670529504239743140D-01 -0.246108664003487376D-03  0.101600349601155157D-03  \n" +
            "  0.122629177626201499D-04 -0.232631259472967761D-05 -0.804108951377953432D-06  \n" +
            "  0.206029905399427166D-07  0.581263821100709364D+08 -0.662210512372681114D+05  \n" +
            " -0.291393309522903117D+06 -0.101744619941463856D+02  0.126929651439618951D+03  \n" +
            "  0.719072745493144483D-01 -0.289875452468910458D-01 -0.116481494733987554D-03  \n" +
            "  0.354904153186168600D-04  0.578114075822577266D-05 -0.586037502516500538D-06  \n" +
            " -0.328565768531919946D-06 -0.870697534704489005D-08 -0.407318221146914810D+08  \n" +
            " -0.200178882850722112D+08  0.208765644130066794D+06  0.166202819332377585D+05  \n" +
            " -0.935330096795945281D+02 -0.474059786835268149D+01  0.223763352193355053D-01  \n" +
            "  0.711404839811943736D-03  0.133154435886312629D-04  0.833293522508309527D-05  \n" +
            " -0.183048189554000485D-05 -0.123812990166292514D-06  0.113907804739359132D-06  \n" +
            "  0.128204928598705485D+09 -0.543398196106910799D+07 -0.642884280738185626D+06  \n" +
            "  0.458736454596573003D+04  0.279753582915553068D+03 -0.138455884991318379D+01  \n" +
            " -0.633478861490010953D-01  0.513652382974482389D-03  0.770172382701938836D-04  \n" +
            " -0.148683630600185154D-04 -0.103473930628158497D-05  0.844548386670234510D-06  \n" +
            " -0.598501623792648406D-07  0.556866963571786806D+08 -0.236346463032116322D+07  \n" +
            " -0.279403534270781209D+06  0.199505441071123209D+04  0.121575220907475469D+03  \n" +
            " -0.600991806760553970D+00 -0.275499358830502902D-01  0.178757622479366836D-03  \n" +
            "  0.376866320331641522D-04 -0.487279388794995223D-05 -0.780710442061747825D-06  \n" +
            "  0.339833716878017756D-06 -0.592326078059037642D-08 -0.114913516258430555D+09  \n" +
            " -0.280217576614845395D+08  0.504556561944404617D+06  0.184447057946873756D+05  \n" +
            " -0.248062881833436109D+03  0.632605793007476325D+00  0.163160027379046363D-01  \n" +
            " -0.128340143129467401D-02  0.220215745937509310D-04 -0.158494237184849485D-05  \n" +
            " -0.583832343185970990D-07  0.192802720181541532D+09 -0.122110898033447359D+08  \n" +
            " -0.841967561744586099D+06  0.119996280241329787D+05  0.175007993882934016D+03  \n" +
            " -0.299041373676346200D+01  0.637064599112446839D-01 -0.629848142119499564D-03  \n" +
            " -0.152653344084663862D-04  0.566214234998166223D-06  0.186274662523543663D-06  \n" +
            "  0.916190637861341685D+08 -0.481510481742066331D+07 -0.400150235871174140D+06  \n" +
            "  0.498557953756415827D+04  0.871803287808562430D+02 -0.138869287765007865D+01  \n" +
            "  0.287519778842222004D-01 -0.250250460578988181D-03 -0.697495550816096685D-05  \n" +
            "  0.309104057693818486D-06  0.691507988025318116D-07 -0.602093049039982557D+09  \n" +
            " -0.120317490097135156D+08  0.749035179423843074D+05  0.216595451488373499D+03  \n" +
            " -0.858234770510209799D+00 -0.167476700578620216D-03  0.366251409028586997D-05  \n" +
            "  0.152712875695776496D-06  0.475847637033618629D+09 -0.118580177899583168D+08  \n" +
            " -0.591625196684676484D+05  0.271516478737068951D+03  0.387896968336449488D+00  \n" +
            " -0.176063032546583398D-02  0.381324173903567499D-05 -0.577304826411968302D-07  \n" +
            "  0.218889726759284317D+09 -0.479129773273426201D+07 -0.272167822181570082D+05  \n" +
            "  0.111153762264181452D+03  0.187458782177848698D+00 -0.751778706079839383D-03  \n" +
            "  0.154962395291772549D-05 -0.225314873120265707D-07 -0.129550402542886043D+10  \n" +
            "  0.534153751758323237D+07  0.264617496120659052D+05 -0.245817229580366181D+02  \n" +
            " -0.336944956708524201D-01  0.441677326463154680D-04 -0.783833473692507480D-07  \n" +
            " -0.637475144814733028D+09 -0.109238522566519659D+08  0.130194660404596962D+05  \n" +
            "  0.341020995767325061D+02 -0.330756137785673809D-01 -0.109224511768588549D-04  \n" +
            " -0.502418356774969979D-07 -0.207668802919756830D+09 -0.473010621191853471D+07  \n" +
            "  0.424116368584627162D+04  0.151022847012223718D+02 -0.122103177042717798D-01  \n" +
            " -0.647042835919428054D-05 -0.193177427973565512D-07  0.241149241785373831D+10  \n" +
            " -0.553440959373824112D+07 -0.590264923445302884D+04  0.194826210749873896D+01  \n" +
            "  0.123051664243913582D-02 -0.651991812706414082D-07  0.158460757979027295D+10  \n" +
            "  0.660736189177446906D+07 -0.387977710295665838D+04 -0.290162710049872707D+01  \n" +
            "  0.495022515339329497D-03  0.376419365339822952D-06  0.659805791860786796D+09  \n" +
            "  0.297368544569101324D+07 -0.161552787343059117D+04 -0.129906142770337873D+01  \n" +
            "  0.199149891970934885D-03  0.168151550815201176D-06 -0.397468598659731817D+10  \n" +
            " -0.358507032861069217D+07  0.275593896423004799D+04  0.395778752066451633D+00  \n" +
            " -0.172025452618570899D-03 -0.545080910650143249D-07  0.193024365112813497D+10  \n" +
            " -0.612572314749610610D+07 -0.133848601484088863D+04  0.716065991547773839D+00  \n" +
            "  0.694128301970037590D-04  0.223695189275242645D-07  0.888893214282933235D+09  \n" +
            " -0.241819352535124402D+07 -0.616372768676741543D+03  0.283230003011654574D+00  \n" +
            "  0.325413069330095385D-04  0.101727594704967644D-07  0.613988689088372993D+10  \n" +
            " -0.196691356406923779D+07 -0.101297164708303239D+04  0.871638808003862148D-01  \n" +
            " -0.408724897819077842D-06  0.298895331352545511D-09  0.384536702748920774D+10  \n" +
            "  0.437254249426266737D+07 -0.634459281106166713D+03 -0.995477659825529104D-01  \n" +
            "  0.783451717457354375D-05  0.202912209687185022D-08 -0.650147202624473810D+09  \n" +
            "  0.195603347214458603D+07  0.107284154852569785D+03 -0.572763131506799222D-01  \n" +
            "  0.264709562436880772D-05  0.501867660229167391D-10 -0.380146387708771857D+06  \n" +
            "  0.264543564448120269D+05  0.180039857099805922D+05 -0.206288645737417767D+03  \n" +
            " -0.552936542493419481D+02  0.253902112545446937D-01 -0.591718785063553229D-01  \n" +
            "  0.338642621142032059D-02  0.414637877937146248D-03 -0.537113404417779852D-05  \n" +
            "  0.311182804516651576D-06 -0.587225245805596341D-07 -0.416912263953140089D-08  \n" +
            " -0.437943891708589799D+05 -0.144808997828707012D+06  0.198569483911786233D+04  \n" +
            "  0.110505367578208370D+04 -0.574267847912343310D+01 -0.959631954519050479D+00  \n" +
            " -0.278583100185543255D-01 -0.679709413530903833D-02  0.165289159432507177D-03  \n" +
            "  0.103920613184913101D-04  0.298847637937984285D-06  0.568401815477760543D-07  \n" +
            " -0.382567382193596550D-08 -0.525920192584513425D+05 -0.703762684263673582D+05  \n" +
            "  0.245319767400554019D+04  0.538226145251121466D+03 -0.739621297079662732D+01  \n" +
            " -0.484816210111489287D+00 -0.186252617566489836D-01 -0.313006097922393332D-02  \n" +
            "  0.116603650207490857D-03  0.480399889650225612D-05  0.173482669337491261D-06  \n" +
            "  0.237098998439606689D-07 -0.225682252030523722D-08 -0.201793839902072679D+06  \n" +
            "  0.145342527351873345D+06  0.101344626689972956D+05 -0.110760001263605977D+04  \n" +
            " -0.545294500273713538D+02  0.984892906263809431D+00  0.170655077302630798D+00  \n" +
            "  0.927609525445627012D-02 -0.352870966113713852D-03 -0.504753889728474283D-04  \n" +
            " -0.862780841784744984D-06  0.183690105343935885D-06  0.142788001156747905D-07  \n" +
            " -0.277906230698997038D+06 -0.793958189203382353D+05  0.138053259374494446D+05  \n" +
            "  0.775050839737221509D+03 -0.420094873581811115D+02 -0.284140408909942677D+01  \n" +
            " -0.638415636212493337D-01  0.734986834161616229D-02  0.679992782666982796D-03  \n" +
            " -0.385317388053432948D-05 -0.280994565638858272D-05 -0.133230049999017235D-06  \n" +
            "  0.760009163629541107D-08 -0.155338259258232341D+06 -0.279304647492222321D+05  \n" +
            "  0.774575410248509343D+04  0.298462381070775791D+03 -0.255869483193575782D+02  \n" +
            " -0.134517314422893630D+01 -0.178367078807057586D-01  0.444564529763109579D-02  \n" +
            "  0.312005395445885474D-03 -0.607877195383580674D-05 -0.147985438392120892D-05  \n" +
            " -0.517019112479882283D-07  0.498248002819036113D-08  0.119476377060901170D+06  \n" +
            "  0.161638747850576328D+06 -0.687861643624822136D+04 -0.155436117565704603D+04  \n" +
            "  0.142457516699601747D+02  0.552446651371832420D+01  0.732076751290750422D-01  \n" +
            " -0.171037060291177982D-01 -0.394522375126296306D-03  0.776042232770234139D-04  \n" +
            "  0.161993342072270677D-05 -0.449812660955371628D-06 -0.120172117607285340D-07  \n" +
            " -0.307756609615663649D+06  0.524580293260091203D+05  0.172049421521118493D+05  \n" +
            " -0.330893205926980613D+03 -0.884489453194233732D+02 -0.416079003283073900D+00  \n" +
            "  0.263980163411875135D+00  0.614465126321260546D-02 -0.966397488412320377D-03  \n" +
            " -0.237234120068196656D-04  0.519031299380173712D-05  0.135056214195264041D-06  \n" +
            " -0.303785082705236809D-07 -0.144082794443089486D+06  0.394771056172008102D+05  \n" +
            "  0.805827768951444341D+04 -0.293496221301320759D+03 -0.432158009106687828D+02  \n" +
            "  0.247092576812536213D+00  0.138494004536601878D+00  0.167419137404879455D-02  \n" +
            " -0.517097664990784985D-03 -0.553353785370117518D-05  0.273517110326535804D-05  \n" +
            "  0.308126777386504286D-07 -0.162156468030293812D-07  0.336828966169211897D+06  \n" +
            "  0.440881564548355673D+05 -0.206474516012640524D+05 -0.505300166740749887D+03  \n" +
            "  0.105847725244934395D+03  0.226983056534523575D+01 -0.266446681047156797D+00  \n" +
            " -0.318967520234454162D-02  0.347520967099458600D-03 -0.405192796070635660D-04  \n" +
            "  0.798726857700716145D-06  0.392208674859702069D-06 -0.856721121881531264D-08  \n" +
            " -0.937369602352569200D+05  0.151801939932424051D+06  0.559703095933303848D+04  \n" +
            " -0.146968836592542903D+04 -0.361027822935445855D+02  0.496276705361493153D+01  \n" +
            "  0.109345731432822349D+00 -0.968124893696323610D-02  0.144962351280881823D-03  \n" +
            "  0.384676311752821323D-05 -0.408125607516737139D-05  0.746631085551247932D-07  \n" +
            "  0.260365052265773077D-07 -0.191522334109511758D+05  0.796042200482625340D+05  \n" +
            "  0.110625790054592608D+04 -0.778485160333201179D+03 -0.938604025172920942D+01  \n" +
            "  0.267809496209113851D+01  0.328881134518763663D-01 -0.512738863122866664D-02  \n" +
            "  0.101011937626279812D-03 -0.137573963908954761D-05 -0.197895744717221825D-05  \n" +
            "  0.696577233346136840D-07  0.123469751018606915D-07  0.261739125320404564D+06  \n" +
            " -0.115460778668159823D+06 -0.162294793137276956D+05  0.118599422702804941D+04  \n" +
            "  0.835411423681089360D+02 -0.451196301980552938D+01 -0.257394259167811557D+00  \n" +
            "  0.802490296314695845D-02  0.987410528306272624D-03  0.333732269898160159D-04  \n" +
            " -0.486070694306472876D-05 -0.450236268814453521D-06  0.194746839597942172D-07  \n" +
            "  0.197152160813751078D+06  0.124244443664333405D+06 -0.120094378865324106D+05  \n" +
            " -0.119949247238273006D+04  0.680022092636508262D+02  0.431754928745019928D+01  \n" +
            " -0.188072307519109749D+00 -0.138222185355903054D-01 -0.433577175276009357D-05  \n" +
            "  0.606773028173139439D-04  0.426110247844289367D-05 -0.277812466671939560D-06  \n" +
            " -0.351855447264673844D-07  0.120241345123751511D+06  0.526548338330022088D+05  \n" +
            " -0.735592295038074190D+04 -0.503383952490857610D+03  0.410093139578492583D+02  \n" +
            "  0.179293121210708617D+01 -0.115700566216520140D+00 -0.626688086595592227D-02  \n" +
            "  0.800517374422998892D-04  0.331654962831404089D-04  0.173202887626733972D-05  \n" +
            " -0.176453037358451876D-06 -0.160213816543085308D-07 -0.434200273138204066D+05  \n" +
            " -0.174047637591851235D+06  0.231694219971940038D+04  0.160268896700655705D+04  \n" +
            " -0.335189235409123185D+02 -0.506523483548274811D+01  0.247317693772024005D+00  \n" +
            "  0.134554082443363032D-01 -0.135324486295203793D-02 -0.523087043568191618D-04  \n" +
            "  0.790204728861769996D-05  0.208717244321935055D-06 -0.514565926679780349D-07  \n" +
            "  0.320026600358231284D+06 -0.648958369160147140D+04 -0.181139715227964298D+05  \n" +
            "  0.232440500452647257D+03  0.876784037545074852D+02 -0.259019265416107380D+01  \n" +
            " -0.233290420781886165D+00  0.161425920419817071D-01  0.768237601745477730D-03  \n" +
            " -0.891259906136856425D-04 -0.330823959043225211D-05  0.562799172302362490D-06  \n" +
            "  0.119150294916996088D-07  0.156490753426618699D+06 -0.176778054445384150D+05  \n" +
            " -0.888262799924967840D+04  0.249188957152510852D+03  0.411978712004151930D+02  \n" +
            " -0.171809382003254396D+01 -0.965465140880083922D-01  0.920758318258367049D-02  \n" +
            "  0.272979298162664443D-03 -0.490051928403818559D-04 -0.100319879614816518D-05  \n" +
            "  0.299325053326784327D-06  0.170911642059805299D-08 -0.322102555404791550D+06  \n" +
            " -0.930396527363408823D+05  0.161639165448255662D+05  0.616529410059579618D+03  \n" +
            " -0.702421151991129875D+02  0.718343451321680981D+00  0.103908347362493145D+00  \n" +
            " -0.133735577217901826D-01  0.235409953964863168D-03  0.433833606463166177D-04  \n" +
            " -0.331745352674841309D-05  0.106736688700613166D-07  0.172470091645953977D-07  \n" +
            "  0.184469556438650296D+06 -0.121478020650574967D+06 -0.914646819269025946D+04  \n" +
            "  0.108213714653755551D+04  0.184430872540245510D+02 -0.281095849815859333D+01  \n" +
            "  0.133907114201180955D+00  0.151234734297813638D-02 -0.778898731720521721D-03  \n" +
            "  0.293697284124141529D-04  0.144529808836147195D-05 -0.235454030204105388D-06  \n" +
            "  0.696488446711418842D-08  0.654846037384924712D+05 -0.685627689638575393D+05  \n" +
            " -0.323964514098970221D+04  0.593681789293995053D+03  0.342879753969365675D+01  \n" +
            " -0.135138707848897166D+01  0.756863847833651321D-01 -0.347287238161298893D-03  \n" +
            " -0.370910745964367653D-03  0.183131271669936566D-04  0.449864707282633832D-06  \n" +
            " -0.117109107543632295D-06  0.491867538989200263D-08 -0.367979390053982730D+06  \n" +
            "  0.482017000801766917D+05  0.174456650134501106D+05 -0.358100244125849713D+03  \n" +
            " -0.535223401783377781D+02  0.278468310166782473D+00 -0.682411513607709130D-01  \n" +
            "  0.168547479920844331D-02  0.462902629971680081D-03 -0.557208785780515487D-06  \n" +
            "  0.533976190039694311D-06 -0.898064678772197889D-08 -0.633126001857151657D-08  \n" +
            " -0.891494227856295038D+05 -0.140916211765377637D+06  0.426690270818264707D+04  \n" +
            "  0.107924902332882652D+04 -0.123488816128572996D+02 -0.881845529676077433D+00  \n" +
            " -0.115923128775193840D-01 -0.746736743511931290D-02  0.490793691858002531D-04  \n" +
            "  0.108466975190320821D-04  0.103881844044592599D-06  0.822172955140778340D-07  \n" +
            "  0.151524605428813118D-09 -0.753426888243995345D+05 -0.665510117368124775D+05  \n" +
            "  0.359116040968995458D+04  0.511209832040009189D+03 -0.106628716038912366D+02  \n" +
            " -0.420968039667565463D+00 -0.114230004980670052D-01 -0.359893647709511478D-02  \n" +
            "  0.631499384021820114D-04  0.540192178065624780D-05  0.962404138897515546D-07  \n" +
            "  0.404183422583019642D-07 -0.451559521591925810D-09  0.103951118497033557D+07  \n" +
            "  0.539529583695928886D+04 -0.196848409360724368D+02 -0.169443170002713112D+00  \n" +
            "  0.438567085748508396D-03  0.171486712002197426D-03  0.142823692092095143D-05  \n" +
            " -0.108453112485402654D-06 -0.819877778985980278D-08  0.132045819024636107D-07  \n" +
            " -0.111359285690631871D-09 -0.448687635979487794D+06  0.718672194990919161D+04  \n" +
            "  0.181265514101234544D+02 -0.392607342480344873D-01 -0.344385843336166616D-02  \n" +
            "  0.166657527611249287D-04  0.822642380305140280D-05  0.188941981574383189D-06  \n" +
            "  0.264056081624312756D-07 -0.854988231615899080D-08  0.134002716877307793D-10  \n" +
            " -0.227493824569498800D+06  0.293422950227444517D+04  0.833567578493266126D+01  \n" +
            " -0.122738724645319233D-01 -0.151475163273686328D-02 -0.380990589641927278D-05  \n" +
            "  0.409779448244743506D-05  0.109640361087530724D-06  0.150378048238303449D-07  \n" +
            " -0.383474167494435727D-08  0.767153197660830493D-11  0.105013811552492343D+07  \n" +
            "  0.523006375697305157D+04 -0.215640848885004850D+02 -0.136208812614024288D+00  \n" +
            "  0.326771386710624553D-02 -0.411600926958783548D-05 -0.329465318219372298D-04  \n" +
            " -0.416108106476200925D-05 -0.350100934194085468D-06  0.388687941181290671D-08  \n" +
            "  0.232705878307139520D-08 -0.434171262310712948D+06  0.732902455120559807D+04  \n" +
            "  0.173799078838602412D+02 -0.773364459337717303D-01 -0.191753987799138213D-03  \n" +
            "  0.370055902765834520D-03  0.216761633529107477D-04 -0.271142682278024132D-06  \n" +
            " -0.258268240168016149D-06 -0.517013842617530835D-07 -0.426901030269364006D-08  \n" +
            " -0.221559413363639527D+06  0.299995262517593255D+04  0.806347179200685105D+01  \n" +
            " -0.295305194240045067D-01 -0.841054247476129278D-05  0.199210885473737612D-03  \n" +
            "  0.148391973936009287D-04  0.290880102884234126D-06 -0.101016335214920032D-06  \n" +
            " -0.259256062259321356D-07 -0.252779740957450050D-08  0.664183288349748342D-04  \n" +
            "  0.241980091469110523D-06  0.624631412449455841D-06  0.110978647364357013D-06  \n" +
            " -0.307288212421689721D-07 -0.483164691853314603D-08 -0.118222217184470492D-09  \n" +
            "  0.874319257856740935D-10  0.369435341369156411D-10  0.825121877495670141D-12  \n" +
            "  0.215865311781410328D-04 -0.792533830420895370D-06 -0.117918064652551805D-06  \n" +
            "  0.732782320418157375D-07  0.102253673162069384D-07 -0.142833258657980517D-08  \n" +
            " -0.263666922850754587D-09 -0.497130952755173946D-10 -0.362766865891642828D-11  \n" +
            "  0.231299047114945173D-11  0.692778689213287367D-04  0.121690382271081040D-05  \n" +
            " -0.796973183595288165D-06 -0.258895958975626701D-07  0.771056657139297315D-07  \n" +
            "  0.388306402656732370D-08 -0.300374349810158295D-08 -0.194012540404947875D-09  \n" +
            "  0.750967858565828798D-10  0.398197404459672473D-11  0.211058036096769634D-04  \n" +
            "  0.469510213285985169D-06  0.218397714187293566D-08 -0.129746204548502592D-06  \n" +
            " -0.365254030943790412D-08  0.729091388851132529D-08  0.354435071663021060D-09  \n" +
            " -0.218099697919716120D-09 -0.821765982905851871D-11  0.458585044488046302D-11  \n" +
            "  0.712621396754321596D-04  0.224034924232328248D-05  0.559203086139434490D-06  \n" +
            " -0.232101133106004771D-06 -0.502496245103541351D-07  0.153718620006324318D-07  \n" +
            "  0.177527666874344828D-08 -0.598343126216500289D-09 -0.492586247309878957D-10  \n" +
            "  0.175100049777539136D-10  0.207603740249963700D-04 -0.515860926034623569D-06  \n" +
            "  0.284879221182405131D-06  0.860281877306832157D-07 -0.289193331822015873D-07  \n" +
            " -0.408267091805872527D-08  0.142583141258068595D-08  0.107765302748511195D-09  \n" +
            " -0.476580859199327685D-10 -0.294245726915467651D-11  0.737121588063326284D-04  \n" +
            " -0.513002857069472149D-06 -0.243290406826675245D-06  0.196589364889120331D-06  \n" +
            "  0.469175856526386282D-08 -0.532382658725358554D-08  0.548725138959498077D-09  \n" +
            " -0.379145026651861907D-10 -0.216570796018299047D-10  0.416619892050537434D-11  \n" +
            "  0.210724765206397172D-04  0.257045719443503510D-06 -0.267324487939535204D-06  \n" +
            " -0.186275053856238103D-07  0.170473237357456864D-07 -0.387618313865204765D-09  \n" +
            " -0.227038764404625978D-09  0.369835913337659842D-10 -0.240397637637456370D-11  \n" +
            "  0.834413084924303199D-12  0.564501884392837674D-01 -0.121219400569233288D-02  \n" +
            " -0.437808660330413755D-03  0.588491172047523174D-04  0.120695622583693637D-04  \n" +
            " -0.843374998400977125D-06 -0.182561090776103386D-06  0.112293689594334590D-08  \n" +
            "  0.134380544389692137D-08  0.186578298267480823D-09  0.393399574126030493D+00  \n" +
            " -0.563987618472706811D-03  0.131225473034805911D-03  0.310809191266889302D-04  \n" +
            " -0.301814129205040717D-05 -0.601799205837627153D-06  0.255215527475453720D-07  \n" +
            "  0.665443837600957882D-08  0.289980770678769504D-09 -0.108288677746370838D-10  \n" +
            " -0.310383774907265761D+05  0.921076133385440143D+00  0.400831650066009633D-03  \n" +
            " -0.558805879198381118D-04 -0.110949267112517487D-04  0.786595200588859708D-06  \n" +
            "  0.169597688725168611D-06 -0.932064809348290563D-09 -0.127564321072315746D-08  \n" +
            " -0.174258717594714792D-09  0.536035822904629353D-01 -0.856192635280350871D-03  \n" +
            "  0.438569596369071946D-03  0.159570323283799428D-04 -0.117658210891767002D-04  \n" +
            "  0.802392068969858083D-06  0.239804020838896442D-06 -0.272507831918079976D-07  \n" +
            " -0.465209442154900163D-08  0.509379839061045122D-09  0.393632972598911501D+00  \n" +
            "  0.744771986817211904D-03  0.762256656211299466D-04 -0.300257882305404168D-04  \n" +
            "  0.546725369160776285D-06  0.643694757534563141D-06 -0.640665193595389926D-07  \n" +
            " -0.132048896356754753D-07  0.141270370019473779D-08  0.233267169592465090D-09  \n" +
            " -0.310365350137451969D+05  0.920676662181619254D+00 -0.414748020916648724D-03  \n" +
            " -0.138052941396592786D-04  0.110604526729016815D-04 -0.749873047246673430D-06  \n" +
            " -0.222913976087454139D-06  0.253170206346742012D-07  0.429017021197358448D-08  \n" +
            " -0.476281244565931180D-09  0.548432673032795345D-01  0.185707828494353043D-02  \n" +
            "  0.170787840022177539D-03 -0.374305463137124614D-04 -0.417381739841782734D-05  \n" +
            " -0.870047145342946334D-06  0.981663875200973222D-07  0.387345019918000684D-07  \n" +
            " -0.264091237294949791D-08 -0.839990922234961856D-09  0.394907874341328291D+00  \n" +
            "  0.358176225035447614D-03 -0.135963184118468423D-03 -0.114634350465247874D-04  \n" +
            "  0.106385280116820818D-07  0.238913783477177253D-06  0.852137927061286437D-07  \n" +
            " -0.638855912026957146D-08 -0.228777018763265300D-08  0.148739175640803344D-09  \n" +
            " -0.310346964065861102D+05  0.918167839697704435D+00 -0.148105242214021862D-03  \n" +
            "  0.357925048679445009D-04  0.363702325970834470D-05  0.782048041765283553D-06  \n" +
            " -0.878970153196734613D-07 -0.352972332908862083D-07  0.240753863099478523D-08  \n" +
            "  0.763029097965851952D-09  0.577676169191737032D-01  0.455974732500108795D-03  \n" +
            " -0.522826606409395570D-03 -0.288414251615441244D-04  0.121329805601465040D-04  \n" +
            "  0.850207611797547381D-06 -0.155029105152630243D-06 -0.633367683011190734D-08  \n" +
            "  0.121012589086946250D-08 -0.144302319392048284D-09  0.394472569241217375D+00  \n" +
            " -0.723103817817897284D-03 -0.449215455553816242D-04  0.344833892588903001D-04  \n" +
            "  0.216606724460840945D-05 -0.557847060502966482D-06 -0.358003793211744801D-07  \n" +
            "  0.596282055354141504D-08 -0.114350636254086184D-09 -0.247371731632710320D-10  \n" +
            " -0.310328592628720362D+05  0.919534764727627407D+00  0.486210191641242529D-03  \n" +
            "  0.250150024841359982D-04 -0.112269800624929318D-04 -0.774249309417103451D-06  \n" +
            "  0.141401859446429141D-06  0.599092141299327558D-08 -0.107650292997955331D-08  \n" +
            "  0.129143519760175107D-09  0.000000000000000000D+00  0.000000000000000000D+00  ";

}