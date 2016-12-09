package se.piro.advent;

/**
 * Created by Rolf Staflin 2016-12-06 22:46
 */
public class Day06 {


    private static void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        Day06 day06 = new Day06();
        day06.go();
    }

    public void go() {
        int[] counts = new int[26];

        String[] lines = input.split("\n");

        // Part 1

        String answer = "";
        for (int position = 0; position < lines[0].length(); position++) {
            for (int i = 0; i < 26; i++) {
                counts[i] = 0;
            }
            for (String line : lines) {
                char c = line.charAt(position);
                counts[c - 'a']++;
            }
            int highestPosition = -1;
            int highest = -1;
            for (int i = 0; i < 26; i++) {
                if (counts[i] > highest) {
                    highest = counts[i];
                    highestPosition = i;
                }
               // print("Count of " + (char)('a' + i) + " == " + counts[i]);
            }
            print("Highest position: " + highestPosition + " (" + (char)('a' + highestPosition) + ") = " + highest);
            answer += (char)('a' + highestPosition);
        }
        print("The answer, my friend, is " + answer);


        // Part 2

        answer = "";
        for (int position = 0; position < lines[0].length(); position++) {
            for (int i = 0; i < 26; i++) {
                counts[i] = 0;
            }
            for (String line : lines) {
                char c = line.charAt(position);
                counts[c - 'a']++;
            }
            int lowestPosition = -1;
            int lowest = Integer.MAX_VALUE;
            for (int i = 0; i < 26; i++) {
                if (counts[i] < lowest) {
                    lowest = counts[i];
                    lowestPosition = i;
                }
                // print("Count of " + (char)('a' + i) + " == " + counts[i]);
            }
            print("Lowest position: " + lowestPosition + " (" + (char)('a' + lowestPosition) + ") = " + lowest);
            answer += (char)('a' + lowestPosition);
        }
        print("The real answer, my friend, is " + answer);



    }
        String input =
            "cmezkqgn\n" +
            "nmzrgcft\n" +
            "ydpndcps\n" +
            "zjihhows\n" +
            "kvptxsrx\n" +
            "ubbvugwq\n" +
            "pclcquhl\n" +
            "rtddzpes\n" +
            "gfkylkvo\n" +
            "cpxpjjme\n" +
            "qqntjofm\n" +
            "tnvmqrik\n" +
            "cczmxxag\n" +
            "ikbrgpjh\n" +
            "lpeohbro\n" +
            "sgdidbgw\n" +
            "apjhovfs\n" +
            "miwqgpmr\n" +
            "igkccbxe\n" +
            "dcfpfkdv\n" +
            "neaxgnpr\n" +
            "xjlnhgwz\n" +
            "hbwdbtmt\n" +
            "jaahaztu\n" +
            "xdhkxiwj\n" +
            "kbcnydre\n" +
            "zygzcjxg\n" +
            "pnhlsbyu\n" +
            "gpkfcakg\n" +
            "vlpebsme\n" +
            "fhivcwnn\n" +
            "avscujyu\n" +
            "tckpnxnn\n" +
            "vhtaizda\n" +
            "vghhmhuy\n" +
            "dtzhrwcw\n" +
            "qhbcdaxx\n" +
            "kdoadrvh\n" +
            "yrjzipbd\n" +
            "weqfqmqr\n" +
            "zlkaiefc\n" +
            "zziwfitz\n" +
            "hfdvzpol\n" +
            "opialtmr\n" +
            "wgbarxig\n" +
            "gguytyxk\n" +
            "gwvaqisb\n" +
            "vybedyip\n" +
            "cbcdebwm\n" +
            "twoqbnis\n" +
            "itrspsmt\n" +
            "cqvjpfou\n" +
            "avhpvkbz\n" +
            "xozehrwd\n" +
            "qizmzubk\n" +
            "hpyiulwy\n" +
            "clmrwgdt\n" +
            "uruutjhx\n" +
            "pyvkmpxk\n" +
            "wpjfzzst\n" +
            "hjxjjkup\n" +
            "mdtlnvab\n" +
            "tqwnjufv\n" +
            "nlaxmbxc\n" +
            "nyetqfpn\n" +
            "nmapoequ\n" +
            "aozqvnbx\n" +
            "awuopxxj\n" +
            "jjamjzdr\n" +
            "xsgnpwrv\n" +
            "odpbdulf\n" +
            "nnpddykk\n" +
            "fwkqbeeq\n" +
            "rmpyqcrr\n" +
            "nnrbqymd\n" +
            "advolplo\n" +
            "xfwzojqb\n" +
            "dlxozmgp\n" +
            "mehtypai\n" +
            "qgxmpmza\n" +
            "cyflmzcf\n" +
            "drilfbik\n" +
            "hsrkwohm\n" +
            "lzdcksvs\n" +
            "xtqiuyon\n" +
            "aatvfuvn\n" +
            "tgdwdznm\n" +
            "srlndtlz\n" +
            "kcdtqqov\n" +
            "rjjwcfpr\n" +
            "sqmwnyjj\n" +
            "spfagdkw\n" +
            "ffqrocvz\n" +
            "fdncyaef\n" +
            "doymrkhy\n" +
            "nagivkzc\n" +
            "ylvmvlvo\n" +
            "yqnpiqnx\n" +
            "yqiuccji\n" +
            "swugswxs\n" +
            "wlfcvtms\n" +
            "bplwnlqh\n" +
            "dyqqbiop\n" +
            "ugxdfwnu\n" +
            "actfbdnl\n" +
            "hafvcdjm\n" +
            "uxlvddgb\n" +
            "jimpqraf\n" +
            "oovjqvmc\n" +
            "niixikhh\n" +
            "uamcczvl\n" +
            "iqyhtphk\n" +
            "hmgnaqfa\n" +
            "anptkatn\n" +
            "taslmdqh\n" +
            "hrsdlgth\n" +
            "tidxkojm\n" +
            "bozyplbl\n" +
            "viyiykes\n" +
            "bqttiowc\n" +
            "fdygoexj\n" +
            "yxiqrabo\n" +
            "hoqmzyap\n" +
            "qrdjlssb\n" +
            "kpoknmcl\n" +
            "wmfbbpoz\n" +
            "xyfmwzrc\n" +
            "ekgikzyt\n" +
            "furxwelu\n" +
            "gtfoyquj\n" +
            "xhtkpgnb\n" +
            "pqwfaoeh\n" +
            "kgutwopd\n" +
            "gmsrhxhp\n" +
            "yfriofga\n" +
            "kjulfqdc\n" +
            "anyrvwxv\n" +
            "reuufyff\n" +
            "rhhuhyku\n" +
            "muwxqimh\n" +
            "lmmesfgq\n" +
            "buklvija\n" +
            "nrqemlud\n" +
            "waggxokb\n" +
            "dmmtiifd\n" +
            "kgawgnsa\n" +
            "pvwrwdhz\n" +
            "mboaagdf\n" +
            "tugpycjc\n" +
            "yrrurffl\n" +
            "xnpptcxi\n" +
            "wynqznnj\n" +
            "pecxtzem\n" +
            "qsmjkvvd\n" +
            "gbosyfyx\n" +
            "dckxdlle\n" +
            "oyuucewm\n" +
            "rvzinbwp\n" +
            "bwdsapew\n" +
            "qacnmkst\n" +
            "dunstuov\n" +
            "gfrmztat\n" +
            "psehmndx\n" +
            "krhyzbag\n" +
            "trxayqjv\n" +
            "ddhrarzx\n" +
            "msnjiwaf\n" +
            "znjklkrs\n" +
            "gzhgcuqn\n" +
            "eoivvakl\n" +
            "ekjbelae\n" +
            "oxvbtsmk\n" +
            "mwfqyskr\n" +
            "tihtgxtf\n" +
            "hldkxeuc\n" +
            "nnawdxvy\n" +
            "euemeepz\n" +
            "ibnuhhex\n" +
            "ojwihmnv\n" +
            "cfpezewj\n" +
            "vrxjrwia\n" +
            "wgmyafnj\n" +
            "pnrsmxka\n" +
            "ksuwbzlt\n" +
            "uwkupngv\n" +
            "jdajpsal\n" +
            "tbufcuza\n" +
            "jjgptlxn\n" +
            "hxoulqig\n" +
            "gieqsttk\n" +
            "fwjyxnaq\n" +
            "pmfdifiq\n" +
            "qcgjfmsh\n" +
            "bnzqevtw\n" +
            "zlosluzk\n" +
            "pyfrslkb\n" +
            "ivzxjsgx\n" +
            "wahqmige\n" +
            "uhvsplzs\n" +
            "qaatujkd\n" +
            "taryjkox\n" +
            "jwdwisfv\n" +
            "dtwhlvuv\n" +
            "lwlwbjee\n" +
            "wopsiktn\n" +
            "iojihkrw\n" +
            "pwmqgwpk\n" +
            "kepvgmcd\n" +
            "dqgupbhg\n" +
            "srofdewh\n" +
            "ntijingz\n" +
            "osixtaku\n" +
            "isacbsnl\n" +
            "txtaxccj\n" +
            "uuqanmcw\n" +
            "nsuogfzt\n" +
            "yktybcsy\n" +
            "csqjvxog\n" +
            "rrjygfmc\n" +
            "eftdwemr\n" +
            "uxbswaep\n" +
            "zghswtrf\n" +
            "fhlxbray\n" +
            "julloyea\n" +
            "bsxwmvfv\n" +
            "kzatuvcu\n" +
            "mnymrdpq\n" +
            "idejsnhx\n" +
            "kdbpzapz\n" +
            "tzjefanj\n" +
            "ottzlwxh\n" +
            "mifokhqj\n" +
            "lxxbtzjr\n" +
            "wjcblnsd\n" +
            "siiozsqc\n" +
            "iujapalx\n" +
            "ofsvvyuy\n" +
            "zbgpxvrb\n" +
            "aqbilxlp\n" +
            "ncobthcc\n" +
            "sflihopk\n" +
            "pxwtiwam\n" +
            "nmgzdpyj\n" +
            "nhjhaezr\n" +
            "weihbqyp\n" +
            "pkpnbhxp\n" +
            "dlrelmop\n" +
            "mjbvnjuq\n" +
            "qntmdrey\n" +
            "htiluzbi\n" +
            "fingzxbe\n" +
            "mnekisyu\n" +
            "ynfcmhzd\n" +
            "vdzoljfg\n" +
            "wfmscpvw\n" +
            "efvyjhux\n" +
            "gvfkaxjq\n" +
            "rkmkahxl\n" +
            "vhqijllu\n" +
            "kkjpwxlq\n" +
            "londfadk\n" +
            "ohsxywdq\n" +
            "znstqcbb\n" +
            "qtazxfoi\n" +
            "jdqwiadz\n" +
            "mumicrid\n" +
            "uhwfytgm\n" +
            "srqofgqp\n" +
            "gtlqqspw\n" +
            "kxnkrcln\n" +
            "aycqjkay\n" +
            "yvangrcm\n" +
            "tpokdbwt\n" +
            "hmfqugbw\n" +
            "qoymvotr\n" +
            "icjendxu\n" +
            "uqsvumij\n" +
            "bqkqoeul\n" +
            "riarnbdv\n" +
            "zwlltddu\n" +
            "izcmngof\n" +
            "lawuhjjj\n" +
            "fdtnicju\n" +
            "iizykequ\n" +
            "lwrfolub\n" +
            "rknrbikc\n" +
            "yvogoydm\n" +
            "bogzdkiw\n" +
            "obnhuoxn\n" +
            "lzzpupsk\n" +
            "nuefyzzr\n" +
            "azghigtg\n" +
            "mkyduyug\n" +
            "mnteeioi\n" +
            "yhqbtwyx\n" +
            "eaojxpwy\n" +
            "hbbxehvr\n" +
            "omdkihmb\n" +
            "hbcijcio\n" +
            "settptzw\n" +
            "babyhhhe\n" +
            "cdlexgrs\n" +
            "cwrdtzjk\n" +
            "xvtwjacw\n" +
            "lxeykife\n" +
            "szogbxgb\n" +
            "ggxlgisl\n" +
            "kbmrnfro\n" +
            "ioervjsx\n" +
            "pfkodypz\n" +
            "ojgbokwc\n" +
            "jvykzhzc\n" +
            "cmigvhio\n" +
            "wwiowvyo\n" +
            "igwtrxhe\n" +
            "obawztja\n" +
            "yyazfxks\n" +
            "gfqqttue\n" +
            "czmvgttl\n" +
            "aljlhlyo\n" +
            "zczpqnzb\n" +
            "ruofwgrx\n" +
            "bhemgvlr\n" +
            "yzsulgck\n" +
            "eixzpfkh\n" +
            "cbejkdrs\n" +
            "qcsnnfht\n" +
            "ryvlmbiz\n" +
            "nfswleyf\n" +
            "xtoxoitk\n" +
            "ysfgwpmy\n" +
            "zsnapbrq\n" +
            "olqagygt\n" +
            "zmtyqfvd\n" +
            "ztybusgn\n" +
            "zsydzdnl\n" +
            "fkbvfvsq\n" +
            "gwdjudok\n" +
            "juzbnhfe\n" +
            "apivbufk\n" +
            "ozxgeeqa\n" +
            "yvyvuvxh\n" +
            "kexcesza\n" +
            "gqefjmed\n" +
            "hqyolehg\n" +
            "mluggzqh\n" +
            "gkpjfkhg\n" +
            "bmvxtrci\n" +
            "euyduveo\n" +
            "avwdogys\n" +
            "jnserfgo\n" +
            "iysfpsns\n" +
            "nxilicng\n" +
            "rpclnuwl\n" +
            "anxroxpu\n" +
            "fjmenahn\n" +
            "xngxqxxt\n" +
            "ziwltmcm\n" +
            "rdizrucj\n" +
            "wvvwldvq\n" +
            "blyiqvpw\n" +
            "iklfxllo\n" +
            "txueozfv\n" +
            "wapwemje\n" +
            "bztthavf\n" +
            "fkfejluf\n" +
            "iwynejes\n" +
            "mkwpylhy\n" +
            "pmndxgby\n" +
            "vhgdvrbv\n" +
            "fizshysy\n" +
            "phqddggq\n" +
            "bosaehqz\n" +
            "kwsoncrz\n" +
            "pmaethwo\n" +
            "valgeqbq\n" +
            "rcjuatfg\n" +
            "ryaujqvn\n" +
            "urpgwdyv\n" +
            "gdefrqbu\n" +
            "jcpfzans\n" +
            "eywcyjer\n" +
            "xpkacpyo\n" +
            "xqdukuff\n" +
            "lmbaxfqi\n" +
            "tzvnhfms\n" +
            "osqfwpss\n" +
            "ltgvoipl\n" +
            "bcorqrzk\n" +
            "wgccrykp\n" +
            "aaaoczvn\n" +
            "jpbsehyo\n" +
            "qtfzphwh\n" +
            "bpiiwzib\n" +
            "tnxbnwyg\n" +
            "xruheaca\n" +
            "eoxvahaq\n" +
            "dzhcleaw\n" +
            "vwcgptbp\n" +
            "mmqzjwte\n" +
            "gpxrndsm\n" +
            "kdgwktpb\n" +
            "roqqxgvt\n" +
            "tceymtaf\n" +
            "pkelkvvi\n" +
            "jqfguroe\n" +
            "xbrhyuai\n" +
            "jvbizlbh\n" +
            "hhujmghp\n" +
            "xxtagkzc\n" +
            "pxtzfvsy\n" +
            "vlopcrko\n" +
            "lorhgtfj\n" +
            "eyuzxpjt\n" +
            "jxjbdzrs\n" +
            "jfcuqypt\n" +
            "dcmbqqln\n" +
            "stdmubrl\n" +
            "fkvvwbue\n" +
            "mqqhkoqd\n" +
            "lvmnavnr\n" +
            "gtxksotd\n" +
            "dyjdydhj\n" +
            "rknodxpp\n" +
            "nkrbeqgp\n" +
            "lzzlxjub\n" +
            "hfhycqag\n" +
            "zrhtmjcz\n" +
            "tetkoiki\n" +
            "aeicawds\n" +
            "kvverwcb\n" +
            "vkkmanit\n" +
            "ozzoauql\n" +
            "eqjceipv\n" +
            "vjeajvzj\n" +
            "rfbyfkdt\n" +
            "ayudrwvi\n" +
            "ozlumnku\n" +
            "bbmgldja\n" +
            "dwpjacmb\n" +
            "ddyqbnzl\n" +
            "jlrdfzef\n" +
            "quovmsbh\n" +
            "utposqki\n" +
            "howsfhba\n" +
            "rdddsgwx\n" +
            "fcdtcqni\n" +
            "kbhnvmah\n" +
            "cgpbjquu\n" +
            "qjhmpyff\n" +
            "wxkytidy\n" +
            "ssefidnf\n" +
            "opswmrqz\n" +
            "zhcskfsp\n" +
            "hhkqbfon\n" +
            "uvgdhifc\n" +
            "eoewusji\n" +
            "xjmylrdx\n" +
            "fabeoujy\n" +
            "gzrceopo\n" +
            "fxsivztv\n" +
            "veqxwblf\n" +
            "sacoxlhm\n" +
            "xongcuef\n" +
            "lufmhuoi\n" +
            "juzgavxq\n" +
            "jjwlcfjq\n" +
            "egmnqjqn\n" +
            "ryhlipod\n" +
            "uagzcjur\n" +
            "epjngrwa\n" +
            "fijrzmww\n" +
            "zihnvpgp\n" +
            "zjurrctz\n" +
            "irhnbjjr\n" +
            "mlrfavaa\n" +
            "cokssyim\n" +
            "auwsrcsm\n" +
            "wrkkttyo\n" +
            "cmskryli\n" +
            "mrkpezgq\n" +
            "ehefyaqv\n" +
            "ivsuxdll\n" +
            "gscbkguh\n" +
            "bfxberbd\n" +
            "vihesdxg\n" +
            "vdbxzltv\n" +
            "lkoiranw\n" +
            "qcnefblb\n" +
            "cfftjwud\n" +
            "xqpieetw\n" +
            "crnrywvn\n" +
            "eepxytfc\n" +
            "cacfhgnf\n" +
            "bakhanwy\n" +
            "lsnlnmrj\n" +
            "usaurokx\n" +
            "sjqbyile\n" +
            "lvcgmrte\n" +
            "vesupotm\n" +
            "yeusftiz\n" +
            "clnjmcit\n" +
            "jhexzuyh\n" +
            "wtbiuozi\n" +
            "fsnqljcg\n" +
            "fxretbsa\n" +
            "lsagjnhx\n" +
            "jjknskzr\n" +
            "dllskstv\n" +
            "vgxhdbyw\n" +
            "yryqoqgz\n" +
            "ycilkokz\n" +
            "vfdcsamh\n" +
            "oedmwosl\n" +
            "vzwfymbu\n" +
            "eqrznqgp\n" +
            "fevhvwom\n" +
            "qextbmed\n" +
            "ubdsfkiu\n" +
            "stvuqrka\n" +
            "nmcrshqw\n" +
            "zlfzaxmw\n" +
            "qzcagqcq\n" +
            "djudatbg\n" +
            "usknomtt\n" +
            "busciicd\n" +
            "wyugburo\n" +
            "qblpvrxc\n" +
            "shzawivm\n" +
            "ztgzrklm\n" +
            "ahpxtdmz\n" +
            "obvuhnlj\n" +
            "uihsumey\n" +
            "mircsnyv\n" +
            "ijjhkyjw\n" +
            "dgxmzhgq\n" +
            "rqavgasa\n" +
            "lelkschr\n" +
            "svzzvroa\n" +
            "sevzfvbh\n" +
            "kgzcpbdj\n" +
            "wvctsjcp\n" +
            "kgdrxolj\n" +
            "tlsksbdi\n" +
            "ycqvhidx\n" +
            "epcaeqir\n" +
            "xcrgjgzi\n" +
            "snuvvmmy\n" +
            "cxbxoxvb\n" +
            "leykoxno\n" +
            "ppvysjob\n" +
            "eubrylie\n" +
            "pxspjeqg\n" +
            "xbdesmuq\n" +
            "bfcpktpy\n" +
            "elyounyn\n" +
            "niwhwuak\n" +
            "hukkheui\n" +
            "ueojrjoc\n" +
            "mktpkpsk\n" +
            "uxljxoei\n" +
            "hymwnsrf\n" +
            "sgyywcqt\n" +
            "yznoeeft\n" +
            "puvcmnpe\n" +
            "domsvurc\n" +
            "ukbhxndd\n" +
            "qwlzklcm\n" +
            "qttwpwdc\n" +
            "vxljmley\n" +
            "sjlbsszg\n" +
            "iqobsomn\n";
}
