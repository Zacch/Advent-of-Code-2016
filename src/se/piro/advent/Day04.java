package se.piro.advent;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Rolf Staflin 2016-12-06 13:49
 */
public class Day04 {



    private static void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        Day04 day04 = new Day04();
        day04.go();
    }

    public void go() {
        print(new Room("aaaaa-bbb-z-y-x-123[abxyz]").toString());
        print(new Room("not-a-real-room-404[oarel]").toString());
        print(new Room("a-b-c-d-e-f-g-h-987[abcde]").toString());

        String[] lines = input.split("\n");
        int sum = 0;
        for (String line: lines) {
            Room room = new Room(line);
            if (room.isValid()) {
                print(room.toString());
                sum += room.sectorId;
            }
        }
        print("Sum: " + sum);
    }

    public class Room {
        String fullText;
        String description;
        int sectorId;
        String checksum;

        public Room(String description) {
            this.fullText = description;
            this.description = description.substring(0, description.lastIndexOf('-'));
            this.checksum = description.substring(description.length() - 6, description.length() - 1);
            String numberPart = description.substring(description.lastIndexOf('-') + 1, description.indexOf('['));
            sectorId = Integer.parseInt(numberPart);
        }

        boolean isValid() {
            if (fullText.length() < 6) return false;
            String actualChecksum = makeChecksum(fullText);
            return checksum.equalsIgnoreCase(actualChecksum);
        }

        private int highestValue;

        private String makeChecksum(String room) {
            String withoutChecksum = room;
            if (withoutChecksum.contains("[")) {
                withoutChecksum = withoutChecksum.substring(0, withoutChecksum.indexOf('['));
            }
            Map<Character, Integer> occurenceMap = new TreeMap<Character, Integer>();

            highestValue = 1;
            withoutChecksum.chars()
                    .mapToObj(i -> (char) i)
                    .forEach(c -> {
                        if (Character.isAlphabetic(c)) {
                            if (occurenceMap.containsKey(c)) {
                                int value = occurenceMap.get(c) + 1;
                                occurenceMap.put(c, value);
                                if (value > highestValue) {
                                    highestValue = value;
                                }
                            } else {
                                occurenceMap.put(c, 1);
                            }
                        }
                    });

            StringBuilder stringBuilder = new StringBuilder();
            for (int value = highestValue; value > 0; value--) {
                for (Character c : occurenceMap.keySet()) {
                    if (occurenceMap.get(c) == value) {
                        stringBuilder.append(c);
                    }
                }
            }

            String result = stringBuilder.toString();
            return result.substring(0, Math.min(5, result.length()));
        }

        public String decrypt() {
            int shiftPositions = sectorId % 26;
            StringBuilder cleartext = new StringBuilder(description.length());
            description.chars()
                    .forEach(i -> {
                        if ((char) i == '-') {
                            cleartext.append(' ');
                        } else {
                            int clear = i + shiftPositions;
                            if (clear > 'z') clear -= 26;

                            cleartext.append((char) clear);
                        }
                    });
            return cleartext.toString();
        }

        public String toString() {
            return decrypt() + "     " + fullText + ", num " + sectorId + ", cs " + checksum + ", valid " + isValid();
        }
    }

    public static final String input =
            "vxupkizork-sgmtkzoi-pkrrehkgt-zxgototm-644[kotgr]\n" +
            "mbiyqoxsm-pvygob-nocsqx-900[obmqs]\n" +
            "veqtekmrk-ikk-hitpscqirx-334[nrtws]\n" +
            "gvcskirmg-fyrrc-irkmriivmrk-932[rikmc]\n" +
            "xmtjbzidx-xviyt-yzqzgjkhzio-187[yzfeu]\n" +
            "bwx-amkzmb-kivlg-kwibqvo-lmaqov-798[bkmva]\n" +
            "vcibutulxiom-ohmnuvfy-yaa-mylpcwym-890[iyaun]\n" +
            "ajvyjprwp-lqxlxujcn-jwjuhbrb-251[muivb]\n" +
            "szfyrqriuflj-avccpsvre-kirzezex-971[rezcf]\n" +
            "kwvacumz-ozilm-akidmvomz-pcvb-uizsmbqvo-590[uslyz]\n" +
            "ucynmlgxcb-afmamjyrc-jmegqrgaq-626[rybxt]\n" +
            "oaxadrgx-pkq-abqdmfuaze-872[xtbnw]\n" +
            "tfejldvi-xiruv-tcrjjzwzvu-gcrjkzt-xirjj-tfekrzedvek-971[krmax]\n" +
            "pwcvonofrcig-suu-rsgwub-740[baiys]\n" +
            "udskkaxawv-tskcwl-dgyaklauk-632[lqcfd]\n" +
            "vqr-ugetgv-dcumgv-fgxgnqrogpv-258[ctwyp]\n" +
            "laffe-igtje-gtgreyoy-124[uscwl]\n" +
            "iehepwnu-cnwza-oywrajcan-dqjp-lqnydwoejc-368[sypjw]\n" +
            "mbiyqoxsm-excdklvo-pvygob-bocokbmr-640[hslwz]\n" +
            "houngfgxjuay-pkrrehkgt-jkvruesktz-930[nsbth]\n" +
            "tcfkqcevkxg-lgnnadgcp-eqpvckpogpv-388[ausbh]\n" +
            "hcd-gsqfsh-wbhsfbohwcboz-pibbm-aofyshwbu-532[bhsfo]\n" +
            "jyfvnlupj-zjhclunly-obua-huhsfzpz-461[jopil]\n" +
            "pbybeshy-rtt-fgbentr-819[vxcue]\n" +
            "kwtwznct-akidmvomz-pcvb-lmaqov-564[mvack]\n" +
            "pdjqhwlf-udeelw-uhdftxlvlwlrq-595[ldwef]\n" +
            "aoubshwq-gqojsbusf-vibh-gsfjwqsg-922[qfetx]\n" +
            "wfintfhynaj-wfruflnsl-gzssd-ijajqturjsy-931[mkhsc]\n" +
            "qspkfdujmf-kfmmzcfbo-eftjho-675[skbda]\n" +
            "dmbttjgjfe-fhh-ufdiopmphz-129[nbqyz]\n" +
            "tinnm-tzcksf-obozmgwg-194[mduaf]\n" +
            "iwcjapey-qjopwxha-ywjzu-ykwpejc-skngodkl-706[kjfog]\n" +
            "kwtwznct-xzwrmkbqtm-lgm-bmkpvwtwog-356[wmtkb]\n" +
            "bkwzkqsxq-wkqxodsm-mrymyvkdo-nozkbdwoxd-640[unlfi]\n" +
            "avb-abpfdk-185[cwyms]\n" +
            "xfbqpojafe-kfmmzcfbo-vtfs-uftujoh-857[nserj]\n" +
            "gsvvswmzi-jpsaiv-pefsvexsvc-516[hbidf]\n" +
            "tfejldvi-xiruv-treup-kvtyefcfxp-321[mntlv]\n" +
            "sxdobxkdsyxkv-mrymyvkdo-mecdywob-cobfsmo-588[emgkf]\n" +
            "luxciuwncpy-vohhs-mylpcwym-292[yiojz]\n" +
            "ide-htrgti-hrpktcvtg-wjci-bpcpvtbtci-583[tcipb]\n" +
            "surmhfwloh-fkrfrodwh-hqjlqhhulqj-621[hflqr]\n" +
            "zuv-ykixkz-yigbktmkx-natz-xkikobotm-540[zyvui]\n" +
            "xmtjbzidx-wpiit-hvivbzhzio-759[kgzsj]\n" +
            "mvkccspson-nio-ckvoc-666[mnklu]\n" +
            "zixppfcfba-yrkkv-ildfpqfzp-991[amdtz]\n" +
            "vjpwncrl-bljenwpna-qdwc-cajrwrwp-251[tgzku]\n" +
            "gntmfefwitzx-xhfajsljw-mzsy-uzwhmfxnsl-671[fmswx]\n" +
            "ajmrxjlcren-yujbcrl-pajbb-fxatbqxy-407[tszqw]\n" +
            "votubcmf-cjpibabsepvt-qmbtujd-hsbtt-nbobhfnfou-129[btfou]\n" +
            "etyyx-rbzudmfdq-gtms-nodqzshnmr-339[okzyu]\n" +
            "bxaxipgn-vgpst-rpcsn-rdcipxcbtci-557[nmtks]\n" +
            "zlilocri-oxyyfq-pxibp-939[ilopx]\n" +
            "xqvwdeoh-hjj-uhvhdufk-985[uixsv]\n" +
            "frqvxphu-judgh-gbh-vhuylfhv-907[gxkts]\n" +
            "rkpqxyib-oxyyfq-obpbxoze-315[jysdq]\n" +
            "ckgvutofkj-jek-giwaoyozout-436[tsfzu]\n" +
            "zilqwikbqdm-ntwemz-lmaqov-616[mqilw]\n" +
            "pkl-oaynap-acc-qoan-paopejc-446[muyva]\n" +
            "lxuxaodu-ajkkrc-bnaerlnb-771[yzint]\n" +
            "wfruflnsl-gzssd-ijuqtdrjsy-515[zdeyi]\n" +
            "mvhkvbdib-wpiit-nzmqdxzn-421[tryva]\n" +
            "aoubshwq-foppwh-sbuwbssfwbu-558[zhyen]\n" +
            "encuukhkgf-gii-hkpcpekpi-414[kipce]\n" +
            "sehheiylu-rkddo-bqrehqjeho-608[pfmot]\n" +
            "irdgrxzex-avccpsvre-ivtvzmzex-529[intur]\n" +
            "ykhknbqh-ywjzu-klanwpekjo-784[khjnw]\n" +
            "gpewwmjmih-tvsnigxmpi-fewoix-vigimzmrk-594[unmjc]\n" +
            "vxupkizork-vrgyzoi-mxgyy-gtgreyoy-826[kmnot]\n" +
            "qmpmxevc-kvehi-wgezirkiv-lyrx-xiglrspskc-438[gdkrm]\n" +
            "ixccb-fkrfrodwh-orjlvwlfv-933[nyhrz]\n" +
            "lujbbrornm-ljwmh-lxjcrwp-lxwcjrwvnwc-511[wjlrc]\n" +
            "eqpuwogt-itcfg-ecpfa-wugt-vguvkpi-934[nchrl]\n" +
            "nwlddtqtpo-mldvpe-wlmzclezcj-691[gznml]\n" +
            "rdchjbtg-vgpst-rpcsn-rdpixcv-uxcpcrxcv-453[zyutx]\n" +
            "willimcpy-wuhxs-xypyfijgyhn-942[vxyeb]\n" +
            "qzchnzbshud-eknvdq-rsnqzfd-521[dnqzh]\n" +
            "froruixo-mhoobehdq-rshudwlrqv-179[mbthd]\n" +
            "mvydjvxodqz-nxvqzibzm-cpio-mzxzdqdib-837[yzntw]\n" +
            "yknnkoera-bhksan-iwjwcaiajp-394[aknij]\n" +
            "xjgjmapg-wpiit-vivgtndn-993[cyqvm]\n" +
            "xgvnndadzy-xjmmjndqz-xviyt-xjvodib-gjbdnodxn-265[kgvsh]\n" +
            "sbnqbhjoh-tdbwfohfs-ivou-fohjoffsjoh-467[ofcde]\n" +
            "cybyjqho-whqtu-zubboruqd-tufbeocudj-842[rqply]\n" +
            "bkwzkqsxq-zvkcdsm-qbkcc-nocsqx-406[xydnz]\n" +
            "tinnm-foadouwbu-dzoghwq-ufogg-obozmgwg-142[rakqb]\n" +
            "rdchjbtg-vgpst-rpcsn-rdpixcv-bpcpvtbtci-765[mkoez]\n" +
            "pinovwgz-mvwwdo-zibdizzmdib-395[smtjh]\n" +
            "pyknyegle-njyqrga-epyqq-bcnjmwkclr-782[yxcjz]\n" +
            "vkrhzxgbv-cxeeruxtg-phkdlahi-709[tyzhs]\n" +
            "udskkaxawv-vqw-vwnwdghewfl-658[wvadk]\n" +
            "gsrwyqiv-kvehi-ikk-asvowlst-438[iksvw]\n" +
            "lahxpnwrl-mhn-vjwjpnvnwc-173[nwhjl]\n" +
            "rzvkjiduzy-ytz-xjiovdihzio-993[sumvy]\n" +
            "avw-zljyla-yhiipa-zhslz-825[mitza]\n" +
            "udpsdjlqj-udglrdfwlyh-mhoobehdq-whfkqrorjb-673[dhjlo]\n" +
            "iruzfrtkzmv-wlqqp-wcfnvi-jyzggzex-841[wszry]\n" +
            "rtqlgevkng-lgnnadgcp-ujkrrkpi-986[wpktr]\n" +
            "ktwbhtvmbox-utldxm-ftgtzxfxgm-995[uqlbn]\n" +
            "dlhwvupglk-qlssfilhu-zopwwpun-149[tvkqz]\n" +
            "gcfcnuls-aluxy-yaa-omyl-nymncha-292[aycln]\n" +
            "jrncbavmrq-enqvbnpgvir-pnaql-qrirybczrag-455[ybfie]\n" +
            "fydelmwp-mfyyj-cplnbftdtetzy-613[whuxk]\n" +
            "vetllbybxw-lvtoxgzxk-angm-mxvaghehzr-475[eswan]\n" +
            "aoubshwq-pibbm-fsoqeiwgwhwcb-662[qfaze]\n" +
            "iutyaskx-mxgjk-vrgyzoi-mxgyy-iayzuskx-ykxboik-722[sxnli]\n" +
            "wihmogyl-aluxy-wuhxs-wiuncha-xyjulngyhn-578[pntgy]\n" +
            "ktiaaqnqml-kzgwomvqk-xtiabqk-oziaa-twoqabqka-226[aqkio]\n" +
            "rgndvtcxr-tvv-ldgzhwde-999[tbefa]\n" +
            "qyujihctyx-vcibutulxiom-wuhxs-wiuncha-xymcah-552[sdmer]\n" +
            "fmsledevhsyw-fyrrc-xiglrspskc-386[yrhfz]\n" +
            "qekrixmg-hci-ywiv-xiwxmrk-490[tynsr]\n" +
            "gcfcnuls-aluxy-vumeyn-yhachyylcha-604[xtfeg]\n" +
            "iutyaskx-mxgjk-pkrrehkgt-gtgreyoy-384[gyblm]\n" +
            "hmsdqmzshnmzk-rbzudmfdq-gtms-kzanqzsnqx-937[grhez]\n" +
            "eqttqukxg-gii-cpcnauku-362[zypxw]\n" +
            "ynukcajey-xekdwvwnzkqo-xqjju-odellejc-316[jglep]\n" +
            "mfklstdw-uzgugdslw-lwuzfgdgyq-320[topmx]\n" +
            "buzahisl-ihzrla-zopwwpun-487[rzsag]\n" +
            "vcibutulxiom-vumeyn-mylpcwym-916[tjvzx]\n" +
            "mvydjvxodqz-nxvqzibzm-cpio-gjbdnodxn-733[xtcbl]\n" +
            "frqvxphu-judgh-udeelw-xvhu-whvwlqj-595[huvwd]\n" +
            "fhezusjybu-tou-skijecuh-iuhlysu-608[uhsei]\n" +
            "houngfgxjuay-vrgyzoi-mxgyy-sgxqkzotm-124[mgfhr]\n" +
            "odkasqzuo-uzfqdzmfuazmx-eomhqzsqd-tgzf-fqotzaxask-508[tmxza]\n" +
            "fydelmwp-upwwjmply-dlwpd-873[gyzjx]\n" +
            "yuxufmdk-sdmpq-dmnnuf-dqeqmdot-742[dmquf]\n" +
            "xst-wigvix-fewoix-wepiw-464[mltfd]\n" +
            "jsehsyafy-tskcwl-dgyaklauk-190[aksyl]\n" +
            "fubrjhqlf-fdqgb-uhdftxlvlwlrq-855[flqbd]\n" +
            "wifilzof-xsy-mbcjjcha-422[olxnw]\n" +
            "bknsykmdsfo-mrymyvkdo-nofovyzwoxd-614[oydkm]\n" +
            "rtqlgevkng-hnqygt-tgceswkukvkqp-232[gkqte]\n" +
            "vjpwncrl-lqxlxujcn-vjatncrwp-407[cjlnp]\n" +
            "sbnqbhjoh-dpssptjwf-fhh-gjobodjoh-935[ckpbr]\n" +
            "vcibutulxiom-dyffsvyuh-lyuwkocmcncih-838[flhey]\n" +
            "nzydfxpc-rclop-dnlgpyrpc-sfye-opawzjxpye-899[pycde]\n" +
            "dzczkrip-xiruv-treup-drerxvdvek-373[rdevi]\n" +
            "ryexqpqhteki-muqfedyput-isqludwuh-xkdj-qdqboiyi-244[qdiue]\n" +
            "vkrhzxgbv-ktfitzbgz-vahvhetmx-vnlmhfxk-lxkobvx-735[smnif]\n" +
            "ajvyjprwp-snuuhknjw-bqryyrwp-329[mdzxw]\n" +
            "wihmogyl-aluxy-luvvcn-guhuaygyhn-188[qjwtv]\n" +
            "xgvnndadzy-zbb-kpmxcvndib-395[bdnvx]\n" +
            "dkqjcbctfqwu-ecpfa-eqcvkpi-ncdqtcvqta-726[paioe]\n" +
            "tbxmlkfwba-avb-cfkxkzfkd-133[gmhkl]\n" +
            "tfcfiwlc-gifavtkzcv-avccpsvre-cfxzjkztj-139[cusqi]\n" +
            "hafgnoyr-enoovg-npdhvfvgvba-351[vgnoa]\n" +
            "lzfmdshb-etyyx-cxd-rsnqzfd-547[clnoz]\n" +
            "sawlkjevaz-zua-nawymqeoepekj-316[fpaeb]\n" +
            "pbafhzre-tenqr-pnaql-pbngvat-znexrgvat-299[iymnw]\n" +
            "dmybmsuzs-nmewqf-emxqe-768[tzycl]\n" +
            "dlhwvupglk-qlssfilhu-lunpullypun-695[tidnq]\n" +
            "hafgnoyr-pubpbyngr-erprvivat-741[vynbz]\n" +
            "odiih-mhn-jlzdrbrcrxw-459[rdhib]\n" +
            "tcfkqcevkxg-tcddkv-tgugctej-986[mejtb]\n" +
            "vkrhzxgbv-ietlmbv-zktll-kxtvjnblbmbhg-189[blvkt]\n" +
            "gsvvswmzi-ikk-eguymwmxmsr-282[bkasy]\n" +
            "jqwpihizlwca-rmttgjmiv-ivitgaqa-694[eibnd]\n" +
            "kzeed-jll-fsfqdxnx-697[abodg]\n" +
            "xtwtelcj-rclop-prr-hzcvdsza-977[crlpt]\n" +
            "nuatmlmdpage-ngzzk-dqmocgueufuaz-872[oldhw]\n" +
            "pybgmyargtc-pyzzgr-kylyeckclr-678[ycgrk]\n" +
            "yhwooebeaz-acc-klanwpekjo-316[xmynz]\n" +
            "qzlozfhmf-azrjds-vnqjrgno-781[ycnzs]\n" +
            "qvbmzvibqwvit-zijjqb-bziqvqvo-252[tcfan]\n" +
            "kfg-jvtivk-nvrgfezqvu-treup-cfxzjkztj-217[vfjkt]\n" +
            "pelbtravp-rtt-bcrengvbaf-845[evbkq]\n" +
            "pkl-oaynap-zua-iwngapejc-420[nkjyh]\n" +
            "qekrixmg-gerhc-gsexmrk-gsrxemrqirx-490[ciuyw]\n" +
            "qczcftiz-pibbm-oqeiwgwhwcb-324[kzdfm]\n" +
            "foadouwbu-qvcqczohs-gvwddwbu-454[qsnwz]\n" +
            "tfiifjzmv-treup-tfekrzedvek-555[usbvt]\n" +
            "nglmtuex-ietlmbv-zktll-kxtvjnblbmbhg-891[lbtme]\n" +
            "ixccb-fdqgb-fxvwrphu-vhuylfh-179[kesml]\n" +
            "rzvkjiduzy-nxvqzibzm-cpio-mznzvmxc-941[vzopy]\n" +
            "nwzekwypera-bhksan-klanwpekjo-498[kaenw]\n" +
            "njmjubsz-hsbef-tdbwfohfs-ivou-xpsltipq-571[sbfhi]\n" +
            "rdchjbtg-vgpst-rdadguja-eaphixr-vgphh-tcvxcttgxcv-765[fzjti]\n" +
            "xfbqpojafe-gmpxfs-vtfs-uftujoh-493[pshct]\n" +
            "tcfkqcevkxg-ecpfa-tgceswkukvkqp-154[kcefg]\n" +
            "hqtyeqsjylu-rkddo-huiuqhsx-530[hquds]\n" +
            "qzoggwtwsr-qcffcgwjs-qvcqczohs-zcuwghwqg-246[jiwnp]\n" +
            "wlqqp-sleep-ivtvzmzex-555[vsoly]\n" +
            "tcfkqcevkxg-dwppa-wugt-vguvkpi-986[hcpyu]\n" +
            "dpmpsgvm-xfbqpojafe-dipdpmbuf-tijqqjoh-805[wgnqv]\n" +
            "ksodcbwnsr-dzoghwq-ufogg-zopcfohcfm-870[dxmtj]\n" +
            "tcfkqcevkxg-dcumgv-wugt-vguvkpi-856[ehozy]\n" +
            "fbebmtkr-zktwx-cxeeruxtg-ftgtzxfxgm-709[tuvsx]\n" +
            "diozmivodjivg-pinovwgz-wvnfzo-rjmfncjk-863[tqdns]\n" +
            "vkppo-zubboruqd-bqrehqjeho-842[qfsjm]\n" +
            "mbiyqoxsm-nio-nozvyiwoxd-614[mstez]\n" +
            "ohmnuvfy-yaa-uhufsmcm-214[muafh]\n" +
            "nzcczdtgp-ojp-afcnsldtyr-769[cdnpt]\n" +
            "tinnm-xszzmpsob-fsqswjwbu-558[sbmnw]\n" +
            "hwbba-uecxgpigt-jwpv-fgrnqaogpv-752[upmvg]\n" +
            "mvydjvxodqz-xcjxjgvoz-jkzmvodjin-187[jvdox]\n" +
            "wdjcvuvmyjpn-mvwwdo-xpnojhzm-nzmqdxz-889[mdjnv]\n" +
            "bpvctixr-qjccn-sthxvc-817[ctvxb]\n" +
            "rdadguja-rwdrdapit-steadnbtci-271[coqre]\n" +
            "kfg-jvtivk-treup-tfrkzex-fgvirkzfej-373[sptcw]\n" +
            "nbhofujd-dboez-dpbujoh-tfswjdft-493[sftdx]\n" +
            "ksodcbwnsr-pogysh-qighcasf-gsfjwqs-116[tvqso]\n" +
            "bqvvu-ywjzu-paydjkhkcu-810[rgpzu]\n" +
            "cvabijtm-lgm-zmamizkp-954[tsrpy]\n" +
            "npmhcargjc-qaytclecp-fslr-rcaflmjmew-834[lgkrj]\n" +
            "aflwjfslagfsd-kusnwfywj-zmfl-ksdwk-164[enmyr]\n" +
            "jlidywncfy-dyffsvyuh-qilembij-422[jmryi]\n" +
            "dmybmsuzs-dmnnuf-dqeqmdot-586[hdgkv]\n" +
            "qspkfdujmf-cbtlfu-mphjtujdt-701[zatkl]\n" +
            "nvrgfezqvu-sleep-ivrthlzjzkzfe-373[udyzv]\n" +
            "tbxmlkfwba-yxphbq-pqloxdb-809[tzsjm]\n" +
            "pelbtravp-enoovg-grpuabybtl-559[oqctv]\n" +
            "dkqjcbctfqwu-ecpfa-ewuvqogt-ugtxkeg-544[cegqt]\n" +
            "xgsvgmotm-xghhoz-lotgtiotm-384[gotmh]\n" +
            "udskkaxawv-jsvagsulanw-kusnwfywj-zmfl-klgjsyw-346[swakj]\n" +
            "krxqjijamxdb-kjbtnc-ujkxajcxah-173[izxny]\n" +
            "myvybpev-lkcuod-nocsqx-770[yzjqg]\n" +
            "upq-tfdsfu-cbtlfu-eftjho-623[ftubc]\n" +
            "npmhcargjc-qaytclecp-fslr-jmegqrgaq-444[nocrz]\n" +
            "iuxxuyobk-vrgyzoi-mxgyy-giwaoyozout-566[kymts]\n" +
            "avw-zljyla-qlssfilhu-klwhyatlua-513[tskre]\n" +
            "mvhkvbdib-kmjezxodgz-agjrzm-mznzvmxc-135[suyop]\n" +
            "qzlozfhmf-azrjds-dmfhmddqhmf-183[dfmhz]\n" +
            "kgjgrypw-epybc-cee-qcptgacq-418[gqyos]\n" +
            "guahyncw-vcibutulxiom-yaa-qilembij-968[xgtow]\n" +
            "lugjuacha-wihmogyl-aluxy-wuhxs-xyjulngyhn-864[qbefr]\n" +
            "iehepwnu-cnwza-ykjoqian-cnwza-zua-lqnydwoejc-680[pfmiz]\n" +
            "mtzslklcozfd-upwwjmply-cpdplcns-743[rngoy]\n" +
            "dfcxsqhwzs-foppwh-difqvogwbu-454[fwdho]\n" +
            "irdgrxzex-jtrmvexvi-ylek-nfibjyfg-139[kwabl]\n" +
            "jvyyvzpcl-tpspahyf-nyhkl-msvdly-ylhjxbpzpapvu-695[oyedt]\n" +
            "qmpmxevc-kvehi-fewoix-qerekiqirx-282[tzsca]\n" +
            "hcd-gsqfsh-suu-gozsg-870[nmehc]\n" +
            "hwdtljsnh-xhfajsljw-mzsy-xytwflj-697[jhlsw]\n" +
            "udpsdjlqj-iorzhu-pdunhwlqj-959[djuhl]\n" +
            "enqvbnpgvir-onfxrg-svanapvat-507[nvagp]\n" +
            "rzvkjiduzy-wpiit-mznzvmxc-213[ntuoi]\n" +
            "gsrwyqiv-kvehi-fyrrc-xiglrspskc-776[hgnvt]\n" +
            "gvaaz-cbtlfu-mbcpsbupsz-103[bacps]\n" +
            "hplazytkpo-mfyyj-opawzjxpye-197[pyajo]\n" +
            "raphhxuxts-gpbepvxcv-rpcsn-rdpixcv-stepgibtci-531[omniy]\n" +
            "cxy-bnlanc-kdwwh-mnyuxhvnwc-251[ditcg]\n" +
            "htsxzrjw-lwfij-hmthtqfyj-yjhmstqtld-307[conpl]\n" +
            "ikhcxvmbex-vtgwr-lxkobvxl-345[cdftu]\n" +
            "sgmtkzoi-pkrrehkgt-lotgtiotm-566[tgkoi]\n" +
            "gbc-frperg-onfxrg-freivprf-455[nymsw]\n" +
            "ide-htrgti-hrpktcvtg-wjci-apqdgpidgn-531[gitdp]\n" +
            "dpmpsgvm-gmpxfs-tupsbhf-259[hgopn]\n" +
            "xmrrq-kusnwfywj-zmfl-wfyafwwjafy-892[fwyaj]\n" +
            "qfmcusbwq-tinnm-pogysh-rsdzcmasbh-428[bgfat]\n" +
            "zhdsrqlchg-lqwhuqdwlrqdo-vfdyhqjhu-kxqw-ghyhorsphqw-803[smjtn]\n" +
            "crwwv-pzxsbkdbo-erkq-pxibp-601[tnzmy]\n" +
            "xgjougizobk-jek-jkvruesktz-592[wsdmo]\n" +
            "bnqqnrhud-okzrshb-fqzrr-cdozqsldms-963[jtram]\n" +
            "qjopwxha-fahhuxawj-qoan-paopejc-472[ahjop]\n" +
            "amppmqgtc-amjmpdsj-afmamjyrc-kylyeckclr-184[yxzuq]\n" +
            "thnulapj-ihzrla-mpuhujpun-643[uhpaj]\n" +
            "lzfmdshb-oqnidbshkd-rbzudmfdq-gtms-btrsnldq-rdquhbd-833[dbqsh]\n" +
            "nzwzcqfw-mldvpe-cpdplcns-119[sejuv]\n" +
            "iqmbazulqp-omzpk-oamfuzs-dqeqmdot-950[qylkj]\n" +
            "lxaaxbren-lqxlxujcn-mnyuxhvnwc-849[frqsy]\n" +
            "nzwzcqfw-xtwtelcj-rclop-nlyoj-nzletyr-xlylrpxpye-743[lycen]\n" +
            "fubrjhqlf-sodvwlf-judvv-rshudwlrqv-621[ghonw]\n" +
            "iuxxuyobk-yigbktmkx-natz-ygrky-514[meayn]\n" +
            "wkqxodsm-nio-myxdksxwoxd-692[rqmey]\n" +
            "jvuzbtly-nyhkl-jvsvymbs-msvdly-klwsvftlua-305[yxmzb]\n" +
            "wlqqp-wcfnvi-uvmvcfgdvek-581[xriqe]\n" +
            "qcffcgwjs-tzcksf-fsqswjwbu-116[ztnym]\n" +
            "lahxpnwrl-kdwwh-mnenuxyvnwc-719[nwhlx]\n" +
            "muqfedyput-cqwdujys-fbqijys-whqii-mehaixef-634[iqefu]\n" +
            "eqpuwogt-itcfg-ejqeqncvg-gpikpggtkpi-232[kzfyv]\n" +
            "iwcjapey-xwogap-lqnydwoejc-420[styzx]\n" +
            "tagzsrsjvgmk-jsttal-mkwj-lwklafy-502[nrteu]\n" +
            "frqvxphu-judgh-udeelw-sxufkdvlqj-725[hzbtv]\n" +
            "kzgwomvqk-kivlg-kwibqvo-uizsmbqvo-746[epcdb]\n" +
            "ckgvutofkj-pkrrehkgt-ygrky-696[yblnm]\n" +
            "ydjuhdqjyedqb-vbemuh-tuiywd-894[oybua]\n" +
            "dwbcjkun-mhn-bjunb-121[srjlk]\n" +
            "ejpanjwpekjwh-qjopwxha-acc-qoan-paopejc-290[bsjiz]\n" +
            "irgyyolokj-inuiurgzk-xkgiwaoyozout-774[oigku]\n" +
            "mfklstdw-xdgowj-vwhsjlewfl-320[xyfdh]\n" +
            "sawlkjevaz-xwogap-klanwpekjo-420[mpkso]\n" +
            "fbebmtkr-zktwx-vhehkyne-lvtoxgzxk-angm-vnlmhfxk-lxkobvx-241[kxvbe]\n" +
            "irdgrxzex-vxx-glityrjzex-893[sxqom]\n" +
            "hqtyeqsjylu-rkddo-cqhaujydw-946[gastx]\n" +
            "lujbbrornm-kjbtnc-bjunb-251[xazbt]\n" +
            "iehepwnu-cnwza-bhksan-zarahkliajp-680[hlqnu]\n" +
            "xcjxjgvoz-vivgtndn-629[yubnv]\n" +
            "ixeumktoi-lruckx-sgtgmksktz-332[mrldq]\n" +
            "qxdwpopgsdjh-rpcsn-rdpixcv-ldgzhwde-765[rxoey]\n" +
            "glrcplyrgmlyj-njyqrga-epyqq-pcacgtgle-158[stgxu]\n" +
            "ojk-nzxmzo-kgvnodx-bmvnn-nzmqdxzn-525[nzmox]\n" +
            "froruixo-mhoobehdq-fxvwrphu-vhuylfh-933[ycnsx]\n" +
            "hdgdovmt-bmvyz-wvnfzo-omvdidib-395[hdygz]\n" +
            "aczupnetwp-ncjzrpytn-nlyoj-nzletyr-ecltytyr-535[ntyce]\n" +
            "fnjyxwrinm-ajkkrc-cajrwrwp-537[nczta]\n" +
            "rgllk-gzefmnxq-omzpk-oamfuzs-fqotzaxask-300[ztbqp]\n" +
            "muqfedyput-sqdto-fkhsxqiydw-218[npsrc]\n" +
            "buzahisl-jovjvshal-jvuahputlua-747[lsatz]\n" +
            "iuruxlar-lruckx-sgtgmksktz-826[krugl]\n" +
            "thnulapj-jovjvshal-ylzlhyjo-513[mtegl]\n" +
            "jshzzpmplk-lnn-shivyhavyf-201[hlnps]\n" +
            "qekrixmg-jpsaiv-pefsvexsvc-646[ryhkz]\n" +
            "mhi-lxvkxm-unggr-kxlxtkva-163[fkcax]\n" +
            "lugjuacha-wbiwifuny-wihnuchgyhn-396[huinw]\n" +
            "sbejpbdujwf-tdbwfohfs-ivou-tfswjdft-545[pnmzi]\n" +
            "nwilwcejc-zua-bejwjyejc-524[jcewa]\n" +
            "xlrypetn-awldetn-rcldd-xlcvpetyr-249[jgkyr]\n" +
            "amlqskcp-epybc-bwc-pcacgtgle-990[iyqge]\n" +
            "ktfitzbgz-lvtoxgzxk-angm-ybgtgvbgz-761[gtzbk]\n" +
            "avw-zljyla-ihzrla-huhsfzpz-253[banhu]\n" +
            "pualyuhapvuhs-ihzrla-ylhjxbpzpapvu-903[xsuvo]\n" +
            "qfkkj-nlyoj-xlcvpetyr-587[jklyc]\n" +
            "iruzfrtkzmv-irsszk-rthlzjzkzfe-451[zrkfi]\n" +
            "iqmbazulqp-vqxxknqmz-pqbxakyqzf-534[qxzab]\n" +
            "udskkaxawv-kusnwfywj-zmfl-ljsafafy-892[afksw]\n" +
            "gzefmnxq-ngzzk-etubbuzs-118[yjzkl]\n" +
            "molgbzqfib-ciltbo-zlkqxfkjbkq-809[isjze]\n" +
            "rnqnyfwd-lwfij-ojqqdgjfs-wjhjnansl-671[jnfqw]\n" +
            "fruurvlyh-ixccb-exqqb-uhfhlylqj-751[hlqub]\n" +
            "pinovwgz-zbb-nvgzn-343[sdhnt]\n" +
            "joufsobujpobm-dipdpmbuf-eftjho-181[ltrqy]\n" +
            "qzchnzbshud-rbzudmfdq-gtms-bnmszhmldms-209[seypq]\n" +
            "xfbqpojafe-dboez-sftfbsdi-649[fbdeo]\n" +
            "qjopwxha-ywjzu-zalhkuiajp-238[diyme]\n" +
            "ocipgvke-ecpfa-gpikpggtkpi-310[moyvi]\n" +
            "lxwbdvna-pajmn-vjpwncrl-bljenwpna-qdwc-vjwjpnvnwc-589[pvqom]\n" +
            "glrcplyrgmlyj-zyqicr-pcacgtgle-964[djtcm]\n" +
            "ckgvutofkj-kmm-jkbkruvsktz-254[twvxe]\n" +
            "pyknyegle-bwc-bcnyprkclr-522[oktmn]\n" +
            "amjmpdsj-zyqicr-asqrmkcp-qcptgac-314[campq]\n" +
            "qvbmzvibqwvit-akidmvomz-pcvb-zmamizkp-902[zvsto]\n" +
            "vxupkizork-igtje-iugzotm-jkvruesktz-748[bvyza]\n" +
            "kpvgtpcvkqpcn-dcumgv-wugt-vguvkpi-596[znbmg]\n" +
            "zovldbkfz-ciltbo-abmxoqjbkq-705[tknsq]\n" +
            "tagzsrsjvgmk-xdgowj-jwuwanafy-476[agjws]\n" +
            "mrxivrexmsrep-glsgspexi-hitevxqirx-256[atzwx]\n" +
            "pelbtravp-enoovg-znantrzrag-195[wdneq]\n" +
            "ocipgvke-eqttqukxg-dcumgv-ugtxkegu-102[soynm]\n" +
            "eqnqthwn-ejqeqncvg-tgugctej-908[zkyam]\n" +
            "hwbba-tcddkv-ocpcigogpv-206[vadln]\n" +
            "aflwjfslagfsd-tmffq-ogjckzgh-528[fgajl]\n" +
            "nij-mywlyn-zotts-luvvcn-jolwbumcha-838[lncjm]\n" +
            "jvsvymbs-zjhclunly-obua-zhslz-409[bpesx]\n" +
            "wfintfhynaj-hfsid-htfynsl-htsyfnsrjsy-359[ozaby]\n" +
            "nzcczdtgp-upwwjmply-dpcgtnpd-795[pcdgn]\n" +
            "excdklvo-lkcuod-bomosfsxq-978[kczes]\n" +
            "mrxivrexmsrep-fmsledevhsyw-ikk-vieguymwmxmsr-516[mersi]\n" +
            "willimcpy-yaa-mylpcwym-864[iucxs]\n" +
            "nsyjwsfyntsfq-hfsid-btwpxmtu-957[miqnp]\n" +
            "jvsvymbs-kfl-klzpnu-149[tyzxi]\n" +
            "amlqskcp-epybc-aylbw-amyrgle-bcnjmwkclr-730[ytmsd]\n" +
            "bkwzkqsxq-nio-cdybkqo-562[anfrp]\n" +
            "ide-htrgti-rpcsn-rdpixcv-rdcipxcbtci-635[zcbyd]\n" +
            "aflwjfslagfsd-tmffq-jwkwsjuz-606[fjswa]\n" +
            "foadouwbu-foppwh-oqeiwgwhwcb-714[toynm]\n" +
            "nzydfxpc-rclop-prr-dezclrp-795[tszpi]\n" +
            "mfklstdw-jsttal-ksdwk-944[autpj]\n" +
            "jvsvymbs-jhukf-zlycpjlz-409[borjv]\n" +
            "lnkfaypeha-ydkykhwpa-zalwnpiajp-342[twaxy]\n" +
            "pbybeshy-cynfgvp-tenff-npdhvfvgvba-741[fvbnp]\n" +
            "shoewudys-uww-tufbeocudj-764[iacnf]\n" +
            "iutyaskx-mxgjk-jek-giwaoyozout-566[koagi]\n" +
            "xcitgcpixdcpa-eaphixr-vgphh-hpath-401[xwtyn]\n" +
            "dlhwvupglk-wshzapj-nyhzz-lunpullypun-643[atizk]\n" +
            "jlidywncfy-mwupyhayl-bohn-mbcjjcha-214[wgcya]\n" +
            "eqpuwogt-itcfg-fag-qrgtcvkqpu-960[gqtcf]\n" +
            "fab-eqodqf-eomhqzsqd-tgzf-eqdhuoqe-950[zoeyv]\n" +
            "oazegyqd-sdmpq-nmewqf-pqhqxabyqzf-872[pkotu]\n" +
            "hqtyeqsjylu-uww-qsgkyiyjyed-946[yqejs]\n" +
            "pejji-mrymyvkdo-bomosfsxq-614[mojsy]\n" +
            "gzefmnxq-omzpk-xasuefuoe-378[iutjy]\n" +
            "hjgbwuladw-uzgugdslw-ksdwk-840[wdguk]\n" +
            "kpvgtpcvkqpcn-gii-ujkrrkpi-674[juebo]\n" +
            "encuukhkgf-gii-ugtxkegu-258[gukei]\n" +
            "xcitgcpixdcpa-eaphixr-vgphh-rdcipxcbtci-167[pkozr]\n" +
            "gcfcnuls-aluxy-wuhxs-wiuncha-lyuwkocmcncih-526[cuhln]\n" +
            "dmbttjgjfe-gmpxfs-mphjtujdt-441[jtmdf]\n" +
            "tcfkqcevkxg-hnqygt-tgceswkukvkqp-154[jgnim]\n" +
            "xqvwdeoh-udeelw-uhdftxlvlwlrq-309[swfid]\n" +
            "nzwzcqfw-ojp-wzrtdetnd-665[wzdnt]\n" +
            "shoewudys-sqdto-seqjydw-iqbui-816[zyxmn]\n" +
            "gzefmnxq-rxaiqd-mzmxkeue-300[cxpzy]\n" +
            "sawlkjevaz-zua-zarahkliajp-472[azjkl]\n" +
            "ktfitzbgz-ietlmbv-zktll-lxkobvxl-345[zgpbs]\n" +
            "cjpibabsepvt-gvaaz-cvooz-sftfbsdi-857[absvc]\n" +
            "eqpuwogt-itcfg-gii-ceswkukvkqp-128[rykin]\n" +
            "tbxmlkfwba-ciltbo-pqloxdb-653[yaqrn]\n" +
            "qzchnzbshud-okzrshb-fqzrr-dmfhmddqhmf-495[shpge]\n" +
            "dszphfojd-upq-tfdsfu-gmpxfs-pqfsbujpot-129[tefni]\n" +
            "aietsrmdih-ikk-hitpscqirx-100[ihkrs]\n" +
            "ykhknbqh-ywjzu-ykwpejc-qoan-paopejc-992[mgfrs]\n" +
            "oxmeeuruqp-eomhqzsqd-tgzf-ogefayqd-eqdhuoq-768[dsutf]\n" +
            "nwzekwypera-ywjzu-ykwpejc-yqopkian-oanreya-134[yaewk]\n" +
            "etyyx-idkkxadzm-zmzkxrhr-807[kxzdm]\n" +
            "kgjgrypw-epybc-aylbw-amyrgle-bcnyprkclr-418[yrbcg]\n" +
            "slqryzjc-njyqrga-epyqq-ylyjwqgq-418[saunh]\n" +
            "nuatmlmdpage-qss-xasuefuoe-794[rtesp]\n" +
            "jvuzbtly-nyhkl-msvdly-wbyjohzpun-617[gjidl]\n" +
            "bkzrrhehdc-azrjds-trdq-sdrshmf-417[dregf]\n" +
            "jqwpihizlwca-akidmvomz-pcvb-bmkpvwtwog-122[dywqz]\n" +
            "gntmfefwitzx-idj-jslnsjjwnsl-463[xcwpd]\n" +
            "myvybpev-bkllsd-domrxyvyqi-978[otdny]\n" +
            "qlm-pbzobq-pzxsbkdbo-erkq-xkxivpfp-263[jfzsm]\n" +
            "lujbbrornm-kdwwh-jwjuhbrb-953[svzbm]\n" +
            "dwbcjkun-yaxsnlcrun-ajkkrc-mnyuxhvnwc-303[lntzx]\n" +
            "froruixo-sodvwlf-judvv-ghvljq-595[ghbyq]\n" +
            "hjgbwuladw-ugjjgkanw-xdgowj-vwhsjlewfl-944[jiyqa]\n" +
            "egdytrixat-gpbepvxcv-qjccn-itrwcdadvn-687[xdpsn]\n" +
            "szfyrqriuflj-jtrmvexvi-ylek-uvgrikdvek-477[vbldh]\n" +
            "uzfqdzmfuazmx-pkq-dqmocgueufuaz-352[owbht]\n" +
            "rnqnyfwd-lwfij-gntmfefwitzx-gzssd-qfgtwfytwd-905[fwtdg]\n" +
            "ykhknbqh-xqjju-odellejc-498[yuzlk]\n" +
            "dwbcjkun-ouxfna-bqryyrwp-485[bnruw]\n" +
            "eqttqukxg-etaqigpke-tcddkv-ceswkukvkqp-492[muprn]\n" +
            "jvyyvzpcl-ibuuf-zlycpjlz-487[imbad]\n" +
            "udglrdfwlyh-gbh-whfkqrorjb-127[fvnah]\n" +
            "ytu-xjhwjy-jll-yjhmstqtld-385[zmyui]\n" +
            "yhwooebeaz-bhksan-wymqeoepekj-316[eoabh]\n" +
            "bnmrtldq-fqzcd-qzaahs-sqzhmhmf-755[jmrin]\n" +
            "shoewudys-rkddo-skijecuh-iuhlysu-530[homtc]\n" +
            "pbeebfvir-enoovg-fuvccvat-663[fpume]\n" +
            "dwbcjkun-mhn-mnbrpw-173[wscyp]\n" +
            "dpmpsgvm-sbccju-nbobhfnfou-987[nmzgw]\n" +
            "eqnqthwn-ecpfa-eqcvkpi-gpikpggtkpi-986[pegik]\n" +
            "npmhcargjc-aylbw-bcnjmwkclr-288[xfyca]\n" +
            "qfkkj-mfyyj-dezclrp-301[pdsrv]\n" +
            "hmsdqmzshnmzk-dff-nodqzshnmr-859[mdhns]\n" +
            "cjpibabsepvt-sbccju-bobmztjt-831[sxhat]\n" +
            "oaddaeuhq-omzpk-oamfuzs-dqeqmdot-586[doamq]\n" +
            "qyujihctyx-luvvcn-mbcjjcha-864[pihao]\n" +
            "wihmogyl-aluxy-wuhxs-zchuhwcha-578[meoti]\n" +
            "szfyrqriuflj-upv-jyzggzex-867[hstwn]\n" +
            "sorozgxe-mxgjk-xghhoz-ygrky-930[avuox]\n" +
            "jvsvymbs-yhtwhnpun-lnn-ylzlhyjo-903[yzoki]\n" +
            "hcd-gsqfsh-qcbgiasf-ufors-qobrm-difqvogwbu-350[ukbjs]\n" +
            "sawlkjevaz-xwogap-bejwjyejc-654[blmwy]\n" +
            "qyujihctyx-jfumncw-alumm-mbcjjcha-838[cthpi]\n" +
            "gvaaz-cbtlfu-efqbsunfou-649[fuabc]\n" +
            "njmjubsz-hsbef-dipdpmbuf-dvtupnfs-tfswjdf-805[dvpoe]\n" +
            "dlhwvupglk-wshzapj-nyhzz-vwlyhapvuz-461[jimak]\n" +
            "amppmqgtc-aylbw-amyrgle-pcyaosgqgrgml-314[gamlp]\n" +
            "pyknyegle-afmamjyrc-nspafyqgle-860[yaefg]\n" +
            "joufsobujpobm-dipdpmbuf-sfdfjwjoh-779[dnsfo]\n" +
            "sorozgxe-mxgjk-ixeumktoi-inuiurgzk-zkinturume-488[nupts]\n" +
            "jqwpihizlwca-rmttgjmiv-camz-bmabqvo-122[ybtzv]\n" +
            "hmsdqmzshnmzk-eknvdq-dmfhmddqhmf-469[tdmly]\n" +
            "xgvnndadzy-agjrzm-nzmqdxzn-655[bjpti]\n" +
            "kdijqrbu-fbqijys-whqii-tufbeocudj-790[mvwqd]\n" +
            "zvyvgnel-tenqr-pnaql-npdhvfvgvba-481[bscyz]\n" +
            "kyelcrga-zsllw-qrmpyec-106[sjnmi]\n" +
            "gpewwmjmih-glsgspexi-pskmwxmgw-464[esxzf]\n" +
            "rdadguja-gpqqxi-jhtg-ithixcv-973[snzut]\n" +
            "vkrhzxgbv-xzz-vnlmhfxk-lxkobvx-371[qrtko]\n" +
            "ykjoqian-cnwza-zua-iwjwcaiajp-550[aijwc]\n" +
            "gsvvswmzi-gerhc-gsexmrk-jmrergmrk-516[mdrpu]\n" +
            "iuruxlar-hgyqkz-xkikobotm-930[kioru]\n" +
            "nij-mywlyn-yaa-nluchcha-214[wdicr]\n" +
            "sbejpbdujwf-dboez-dpbujoh-efwfmpqnfou-597[yxwam]\n" +
            "wlsiayhcw-mwupyhayl-bohn-xyjulngyhn-396[nmstq]\n" +
            "vqr-ugetgv-ejqeqncvg-nqikuvkeu-466[eqvgu]\n" +
            "yhwooebeaz-acc-iwjwcaiajp-446[acwei]\n" +
            "wbhsfbohwcboz-dzoghwq-ufogg-fsoqeiwgwhwcb-610[wobgh]\n" +
            "vxupkizork-igtje-uvkxgzouty-384[stomh]\n" +
            "bxaxipgn-vgpst-rpcsn-sthxvc-219[pkyzm]\n" +
            "oxmeeuruqp-omzpk-oamfuzs-etubbuzs-534[fmryb]\n" +
            "enzcntvat-rtt-ernpdhvfvgvba-819[tvnae]\n" +
            "lxwbdvna-pajmn-yujbcrl-pajbb-mnyuxhvnwc-121[qonxs]\n" +
            "kzeed-hmthtqfyj-wjxjfwhm-463[gisbv]\n" +
            "amjmpdsj-njyqrga-epyqq-rpyglgle-444[wyviq]\n" +
            "ftzgxmbv-utldxm-ltexl-241[ltxmb]\n" +
            "lxuxaodu-lxaaxbren-bljenwpna-qdwc-mnenuxyvnwc-745[yustq]\n" +
            "rwcnawjcrxwju-snuuhknjw-cnlqwxuxph-433[bzwvn]\n" +
            "iutyaskx-mxgjk-hgyqkz-xkikobotm-956[tvyep]\n" +
            "gsrwyqiv-kvehi-nippcfier-xiglrspskc-542[iprsc]\n" +
            "diozmivodjivg-xcjxjgvoz-hvivbzhzio-265[pdjnm]\n" +
            "dszphfojd-fhh-mbcpsbupsz-493[hpsbd]\n" +
            "qxdwpopgsdjh-eaphixr-vgphh-stktadebtci-323[mayxe]\n" +
            "pelbtravp-rtt-erprvivat-897[rtpva]\n" +
            "mvydjvxodqz-agjrzm-yzkvmohzio-369[nmwjo]\n" +
            "uqtqbizg-ozilm-zijjqb-lmxizbumvb-200[bizmq]\n" +
            "gvaaz-fhh-usbjojoh-675[ziyxw]\n" +
            "ygcrqpkbgf-fag-ewuvqogt-ugtxkeg-336[gefkq]\n" +
            "eadalsjq-yjsvw-vqw-vwhsjlewfl-294[yjmzc]\n" +
            "vagreangvbany-fpniratre-uhag-npdhvfvgvba-299[ybzws]\n" +
            "atyzghrk-xghhoz-iutzgotsktz-436[pxlky]\n" +
            "uwtojhynqj-hfsid-htfynsl-wjfhvznxnynts-229[nhfjs]\n" +
            "udskkaxawv-tmffq-kzahhafy-658[afkhd]\n" +
            "jvuzbtly-nyhkl-wshzapj-nyhzz-shivyhavyf-591[taisq]\n" +
            "nwzekwypera-xwogap-nawymqeoepekj-862[aiknj]\n" +
            "gsrwyqiv-kvehi-gerhc-tyvglewmrk-490[emcda]\n" +
            "bdavqofuxq-dmnnuf-mzmxkeue-924[stnrq]\n" +
            "cxy-bnlanc-ljwmh-lxjcrwp-jwjuhbrb-199[jbclw]\n" +
            "ryexqpqhteki-uww-iqbui-972[whlao]\n" +
            "guahyncw-zfiqyl-mylpcwym-292[hakyd]\n" +
            "mybbycsfo-tovvilokx-wkbuodsxq-640[pwdms]\n" +
            "sehheiylu-sxesebqju-cqdqwucudj-166[dqtmn]\n" +
            "emixwvqhml-ntwemz-bziqvqvo-512[mqvei]\n" +
            "willimcpy-wuhxs-yhachyylcha-136[wmdkg]\n" +
            "aietsrmdih-jpsaiv-wlmttmrk-802[pndyu]\n" +
            "xst-wigvix-veffmx-irkmriivmrk-854[vzbjm]\n" +
            "dpmpsgvm-dboez-dpbujoh-dvtupnfs-tfswjdf-831[nzcoy]\n" +
            "wlqqp-irsszk-rercpjzj-815[bjyfk]\n" +
            "kyelcrga-aylbw-amyrgle-sqcp-rcqrgle-730[engxw]\n" +
            "ghkmaihex-hucxvm-lmhktzx-501[hmxka]\n" +
            "bgmxkgtmbhgte-cxeeruxtg-ftkdxmbgz-449[gtxbe]\n" +
            "udglrdfwlyh-iorzhu-vklsslqj-751[ldhrs]\n" +
            "fmsledevhsyw-fewoix-asvowlst-282[sewfl]\n" +
            "qfkkj-ojp-cpdplcns-197[yovpm]\n" +
            "ejpanjwpekjwh-iwcjapey-xwogap-zalwnpiajp-108[reijs]\n" +
            "foadouwbu-tzcksf-rsdzcmasbh-428[sabcd]\n" +
            "jyddc-gerhc-gsexmrk-erepcwmw-308[cghmn]\n" +
            "ynukcajey-zua-yqopkian-oanreya-732[aynek]\n" +
            "zotts-vumeyn-guleyncha-968[entuy]\n" +
            "enzcntvat-jrncbavmrq-rtt-fgbentr-871[htygn]\n" +
            "qfkkj-dnlgpyrpc-sfye-dezclrp-353[pcdef]\n" +
            "kzeed-htsxzrjw-lwfij-gzssd-tujwfyntsx-879[pefzg]\n" +
            "gvcskirmg-nippcfier-vieguymwmxmsr-646[zsigf]\n" +
            "ncjzrpytn-nlyoj-hzcvdsza-587[tnijv]\n" +
            "fkqbokxqflkxi-ciltbo-ixyloxqlov-991[tdoma]\n" +
            "tagzsrsjvgmk-uzgugdslw-jwsuimakalagf-814[fzilb]\n" +
            "ydjuhdqjyedqb-vbemuh-tufbeocudj-764[vyuwb]\n" +
            "glrcplyrgmlyj-cee-kypicrgle-574[znbys]\n" +
            "fhezusjybu-sqdto-ixyffydw-244[istpq]\n" +
            "willimcpy-luvvcn-omyl-nymncha-110[lcmny]\n" +
            "ujoon-ytaanqtpc-jhtg-ithixcv-661[nhrwm]\n" +
            "aoubshwq-qobrm-ghcfous-116[ixtcr]\n" +
            "gcfcnuls-aluxy-vohhs-xyjfisgyhn-526[emins]\n" +
            "uiovmbqk-zijjqb-abwziom-382[ymdir]\n" +
            "ubhatstkwhnl-unggr-lmhktzx-371[zskyt]\n" +
            "tagzsrsjvgmk-vqw-klgjsyw-476[nzitq]\n" +
            "lejkrscv-wcfnvi-wzeretzex-451[tefwa]\n" +
            "myxcewob-qbkno-mkxni-oxqsxoobsxq-302[ayius]\n" +
            "tagzsrsjvgmk-usfvq-ljsafafy-242[safgj]\n" +
            "fab-eqodqf-dmnnuf-geqd-fqefuzs-898[igqxs]\n" +
            "mtzslklcozfd-dnlgpyrpc-sfye-dezclrp-639[edrfn]\n" +
            "aoubshwq-gqojsbusf-vibh-fsqswjwbu-376[qpxwo]\n" +
            "oaxadrgx-eomhqzsqd-tgzf-pqbxakyqzf-508[qaxzd]\n" +
            "luxciuwncpy-dyffsvyuh-mufym-682[kpftu]\n" +
            "hcd-gsqfsh-rms-qcbhowbasbh-168[utywx]\n" +
            "eqpuwogt-itcfg-hnqygt-ceswkukvkqp-882[scpud]\n" +
            "kfg-jvtivk-treup-drerxvdvek-841[sjpfi]\n" +
            "jrncbavmrq-vagreangvbany-onfxrg-ynobengbel-585[nabgr]\n" +
            "tcrjjzwzvu-treup-tfrkzex-wzeretzex-113[jacik]\n" +
            "nzydfxpc-rclop-clxalrtyr-mldvpe-wlmzclezcj-535[jwvqg]\n" +
            "cybyjqho-whqtu-tou-kiuh-juijydw-816[zvfwe]\n" +
            "hmsdqmzshnmzk-atmmx-cdoknxldms-365[mdshk]\n" +
            "rgndvtcxr-eaphixr-vgphh-itrwcdadvn-947[nbcsm]\n" +
            "mbggf-wshzapj-nyhzz-mpuhujpun-175[lekij]\n" +
            "iuruxlar-yigbktmkx-natz-sgxqkzotm-306[pkfsv]\n" +
            "nsyjwsfyntsfq-kqtbjw-fsfqdxnx-203[fsnqj]\n" +
            "oknkvcta-itcfg-fag-rwtejcukpi-986[gszvj]\n" +
            "nuatmlmdpage-dmnnuf-qzsuzqqduzs-170[udmnq]\n" +
            "wlqqp-irsszk-ljvi-kvjkzex-659[kijlq]\n" +
            "pualyuhapvuhs-jovjvshal-jvuahputlua-513[dtigq]\n" +
            "yaxsnlcrun-ajmrxjlcren-mhn-ydalqjbrwp-381[axybh]\n" +
            "ykhknbqh-oywrajcan-dqjp-odellejc-732[zmhol]\n" +
            "zekvierkzferc-sleep-jyzggzex-867[xdzch]\n" +
            "fab-eqodqf-nmewqf-oazfmuzyqzf-196[ivrwp]\n" +
            "dpmpsgvm-cvooz-efqmpznfou-311[cdzpy]\n" +
            "awzwhofm-ufors-xszzmpsob-difqvogwbu-272[vxzrc]\n" +
            "lxwbdvna-pajmn-ljwmh-lxjcrwp-bcxajpn-173[rmdts]\n" +
            "yhwooebeaz-ejpanjwpekjwh-zua-owhao-810[sreak]\n" +
            "muqfedyput-tou-fkhsxqiydw-582[udfqt]\n" +
            "glrcplyrgmlyj-njyqrga-epyqq-qcptgacq-756[rlowk]\n" +
            "cjpibabsepvt-joufsobujpobm-cvooz-efqmpznfou-493[wfzrg]\n" +
            "vrurcjah-pajmn-snuuhknjw-nwprwnnarwp-927[nrwaj]\n" +
            "sgmtkzoi-vrgyzoi-mxgyy-yzuxgmk-384[wdglh]\n" +
            "eadalsjq-yjsvw-tskcwl-vwhsjlewfl-294[umvwa]\n" +
            "pdjqhwlf-froruixo-iorzhu-vhuylfhv-985[wqsva]\n" +
            "qczcftiz-dzoghwq-ufogg-obozmgwg-948[ayqfz]\n" +
            "dlhwvupglk-ibuuf-shivyhavyf-227[huvfi]\n" +
            "surmhfwloh-iorzhu-ghyhorsphqw-959[horsu]\n" +
            "uzfqdzmfuazmx-omzpk-etubbuzs-222[jbtnq]\n" +
            "yhkpvhjapcl-jovjvshal-zlycpjlz-903[nhtyz]\n" +
            "wfintfhynaj-hfsid-htfynsl-xjwanhjx-723[fhnja]\n" +
            "enqvbnpgvir-pnaql-pbngvat-erfrnepu-533[nperv]\n" +
            "frqvxphu-judgh-edvnhw-frqwdlqphqw-205[hvwrq]\n" +
            "irdgrxzex-irsszk-ljvi-kvjkzex-139[ikrxz]\n" +
            "sorozgxe-mxgjk-vxupkizork-hatte-jkvgxzsktz-306[zieyv]\n" +
            "qyujihctyx-wbiwifuny-guhuaygyhn-604[yoszc]\n" +
            "lxuxaodu-npp-mnenuxyvnwc-953[wtfho]\n" +
            "myxcewob-qbkno-nio-crszzsxq-250[obcnq]\n" +
            "dpotvnfs-hsbef-kfmmzcfbo-tbmft-831[nzqmy]\n" +
            "oxaflxzqfsb-oxyyfq-cfkxkzfkd-783[fxkoq]\n" +
            "ipvohghykvbz-kfl-jvuahputlua-851[huvak]\n" +
            "buzahisl-jhukf-jvhapun-hjxbpzpapvu-227[ylznv]\n" +
            "nsyjwsfyntsfq-hfsid-htfynsl-yjhmstqtld-879[pdujv]\n" +
            "xfbqpojafe-dipdpmbuf-vtfs-uftujoh-181[ujbvm]\n" +
            "ujqgywfau-ugjjgkanw-usfvq-vwhdgqewfl-450[aczqr]\n" +
            "hafgnoyr-rtt-znantrzrag-481[qyxab]\n" +
            "ovbunmneqbhf-rtt-qrirybczrag-897[rbnqt]\n" +
            "vcibutulxiom-jfumncw-alumm-nywbhifias-318[angjx]\n" +
            "luxciuwncpy-yaa-uhufsmcm-344[eupia]\n" +
            "crwwv-oxaflxzqfsb-yxphbq-abmxoqjbkq-679[bqxaf]\n" +
            "rdchjbtg-vgpst-hrpktcvtg-wjci-ejgrwphxcv-557[gmont]\n" +
            "avw-zljyla-jhukf-ylzlhyjo-617[xmgat]\n" +
            "fydelmwp-awldetn-rcldd-xlylrpxpye-691[umlsi]\n" +
            "amppmqgtc-njyqrga-epyqq-pcqcypaf-522[jmnui]\n" +
            "kmjezxodgz-nxvqzibzm-cpio-omvdidib-135[jifak]\n" +
            "kzgwomvqk-jiasmb-bziqvqvo-616[nxhic]\n" +
            "lxuxaodu-ljwmh-mnyuxhvnwc-303[ekywx]\n" +
            "bwx-amkzmb-kpwkwtibm-uiviomumvb-200[fopng]\n" +
            "yknnkoera-xqjju-nayaerejc-238[imwko]\n" +
            "wlsiayhcw-wifilzof-xsy-lymyulwb-604[lwyif]\n" +
            "tvsnigxmpi-gerhc-gsexmrk-pskmwxmgw-230[dlhtu]\n" +
            "pinovwgz-xcjxjgvoz-adivixdib-343[pmnjr]\n" +
            "lnkfaypeha-xwogap-skngodkl-888[hwxrv]\n" +
            "tvsnigxmpi-hci-asvowlst-568[lrakt]\n" +
            "tipfxvezt-treup-rercpjzj-191[bxghm]\n" +
            "nzydfxpc-rclop-nlyoj-wzrtdetnd-483[xvftz]\n" +
            "bnmrtldq-fqzcd-azrjds-kzanqzsnqx-391[ketyf]\n" +
            "zekvierkzferc-sleep-kirzezex-477[otfzl]\n" +
            "xfbqpojafe-nbhofujd-sbccju-bobmztjt-649[bjfoc]\n" +
            "lgh-kwujwl-hdsklau-yjskk-ljsafafy-320[nzyav]\n" +
            "eqpuwogt-itcfg-ejqeqncvg-yqtmujqr-440[dmpcx]\n" +
            "amlqskcp-epybc-bwc-dglylagle-834[oqwzc]\n" +
            "hvbizodx-kgvnodx-bmvnn-gvwjmvojmt-603[gheqr]\n" +
            "buzahisl-msvdly-zlycpjlz-227[urzfe]\n" +
            "gvaaz-kfmmzcfbo-bdrvjtjujpo-207[jabfm]\n" +
            "nzcczdtgp-qwzhpc-dezclrp-197[czpde]\n" +
            "gsvvswmzi-veffmx-viwievgl-386[znley]\n" +
            "dyz-combod-mkxni-crszzsxq-354[xymnl]\n" +
            "zuv-ykixkz-pkrrehkgt-iutzgotsktz-748[ktzgi]\n" +
            "rgndvtcxr-eaphixr-vgphh-tcvxcttgxcv-531[pntam]\n" +
            "qfmcusbwq-foppwh-rsjszcdasbh-662[sbcfh]\n" +
            "vdzonmhydc-dff-zmzkxrhr-391[pkeyc]\n" +
            "clxalrtyr-mldvpe-nzyeltyxpye-353[dmtcw]\n" +
            "xgjougizobk-inuiurgzk-zkinturume-254[hqutr]\n" +
            "lsyrkjkbnyec-lkcuod-nocsqx-172[axnjt]\n" +
            "oxjmxdfkd-pzxsbkdbo-erkq-jxohbqfkd-965[dmnfi]\n" +
            "rkpqxyib-yxphbq-zrpqljbo-pbosfzb-471[bpqor]\n" +
            "glrcplyrgmlyj-bwc-pcqcypaf-262[clpyg]\n" +
            "mybbycsfo-nio-psxkxmsxq-432[znbfc]\n" +
            "fubrjhqlf-vfdyhqjhu-kxqw-ghvljq-257[xygze]\n" +
            "ajvyjprwp-ajkkrc-vjwjpnvnwc-667[jpvwa]\n" +
            "qfmcusbwq-rms-obozmgwg-740[tjyop]\n" +
            "xlrypetn-mldvpe-nzyeltyxpye-171[jyfxu]\n" +
            "zlkprjbo-doxab-mixpqfz-doxpp-xznrfpfqflk-809[icbft]\n" +
            "vjpwncrl-lqxlxujcn-mnyuxhvnwc-407[sygbx]\n" +
            "wlqqp-treup-tfrkzex-uvjzxe-815[rnolz]\n" +
            "ahngzyzqcntr-idkkxadzm-lzqjdshmf-183[zdahk]\n" +
            "mrxivrexmsrep-jpsaiv-asvowlst-594[ldpzc]\n" +
            "jsvagsulanw-wyy-wfyafwwjafy-918[plqyr]\n" +
            "ixeumktoi-yigbktmkx-natz-aykx-zkyzotm-436[ergam]\n" +
            "pybgmyargtc-njyqrga-epyqq-nspafyqgle-782[ushvj]\n" +
            "kfg-jvtivk-wcfnvi-jrcvj-919[vjcfi]\n" +
            "fubrjhqlf-ixccb-iorzhu-uhvhdufk-309[qkwnm]\n" +
            "muqfedyput-fbqijys-whqii-mehaixef-400[shvuf]\n" +
            "tcrjjzwzvu-avccpsvre-jyzggzex-685[fyzub]\n" +
            "zgmfyxypbmsq-zyqicr-bcnjmwkclr-938[utmil]\n" +
            "houngfgxjuay-pkrrehkgt-yzuxgmk-488[xszwt]\n" +
            "pbeebfvir-pnaql-erprvivat-247[vhuxi]\n" +
            "qvbmzvibqwvit-kpwkwtibm-abwziom-694[rnmtq]\n" +
            "mvhkvbdib-zbb-pnzm-oznodib-395[ldmui]\n" +
            "qxdwpopgsdjh-rwdrdapit-stktadebtci-245[dtpai]\n" +
            "xgsvgmotm-lruckx-sgtgmksktz-514[nestp]\n" +
            "muqfedyput-zubboruqd-huiuqhsx-738[uqbdh]\n" +
            "laffe-hatte-lotgtiotm-566[taefl]\n" +
            "rdchjbtg-vgpst-rpcsn-hidgpvt-921[gptcd]\n" +
            "nwilwcejc-nwxxep-zarahkliajp-160[awcei]\n" +
            "jvyyvzpcl-jyfvnlupj-zjhclunly-obua-ayhpupun-929[zcymu]\n" +
            "gbc-frperg-cynfgvp-tenff-nanylfvf-429[pszmb]\n" +
            "nzwzcqfw-qwzhpc-xlcvpetyr-587[qmjln]\n" +
            "ucynmlgxcb-zyqicr-jmegqrgaq-730[cgqmr]\n" +
            "gbc-frperg-sybjre-grpuabybtl-351[giufw]\n" +
            "hqcfqwydw-hqrryj-mehaixef-608[hqefr]\n" +
            "yflexwxoalrp-zovldbkfz-oxyyfq-rpbo-qbpqfkd-367[foblp]\n" +
            "iqmbazulqp-dmnnuf-fqotzaxask-170[mnuiw]\n" +
            "ixccb-iorzhu-dftxlvlwlrq-439[katfr]\n" +
            "gpsxdprixkt-uadltg-rdcipxcbtci-115[sxkct]\n" +
            "npmhcargjc-qaytclecp-fslr-pcqcypaf-990[soyhd]\n" +
            "hqtyeqsjylu-uww-tufbeocudj-920[zlroy]\n" +
            "clotzlnetgp-ojp-dezclrp-431[lpceo]\n" +
            "wfummczcyx-wuhxs-lywycpcha-110[eatif]\n" +
            "jshzzpmplk-wshzapj-nyhzz-zavyhnl-643[zhpaj]\n" +
            "hqtyeqsjylu-sqdto-seqjydw-bqrehqjeho-946[ltnyb]\n" +
            "eza-dpncpe-mfyyj-cplnbftdtetzy-717[oxwat]\n" +
            "xmtjbzidx-xjgjmapg-ytz-mzvxlpdndodji-603[ksiqx]\n" +
            "ugdgjxmd-vqw-vwhsjlewfl-684[sxfvh]\n" +
            "ynssr-xzz-xgzbgxxkbgz-839[xzgbs]\n" +
            "tagzsrsjvgmk-xdgowj-ljsafafy-918[agjsf]\n" +
            "vrurcjah-pajmn-lqxlxujcn-uxprbcrlb-953[romck]\n" +
            "jshzzpmplk-qlssfilhu-bzly-alzapun-773[ojiwl]\n" +
            "pinovwgz-ytz-xjiovdihzio-655[yrfzp]\n" +
            "pualyuhapvuhs-yhiipa-thuhnltlua-903[hualp]\n" +
            "hcd-gsqfsh-awzwhofm-ufors-dzoghwq-ufogg-fsgsofqv-870[yfizu]\n" +
            "vrurcjah-pajmn-mhn-vjwjpnvnwc-563[rften]\n" +
            "vkrhzxgbv-cxeeruxtg-ybgtgvbgz-215[lswez]\n" +
            "pkl-oaynap-lhwopey-cnwoo-hkceopeyo-316[opeya]\n" +
            "bdavqofuxq-rxaiqd-ymzmsqyqzf-378[hguys]\n" +
            "uqtqbizg-ozilm-ntwemz-wxmzibqwva-876[gzhyw]\n" +
            "jyddc-fyrrc-xvemrmrk-360[ulmzd]\n" +
            "jchipqat-rpcsn-rdpixcv-pcpanhxh-687[ltzwy]\n" +
            "dfcxsqhwzs-xszzmpsob-hsqvbczcum-376[agmnj]\n" +
            "vqr-ugetgv-gii-fgukip-856[giuve]\n" +
            "xmtjbzidx-agjrzm-jkzmvodjin-811[wavdq]\n" +
            "rmn-qcapcr-djmucp-sqcp-rcqrgle-756[sozba]\n" +
            "eadalsjq-yjsvw-tskcwl-suimakalagf-788[hmgzu]\n" +
            "tinnm-qobrm-kcfygvcd-350[lzrje]\n" +
            "oxjmxdfkd-zelzlixqb-pqloxdb-965[xdlbo]\n" +
            "hdgdovmt-bmvyz-ezggtwzvi-omvdidib-863[dvgim]\n" +
            "ajyqqgdgcb-aylbw-amyrgle-cleglccpgle-522[ynhwt]\n" +
            "oazegyqd-sdmpq-ngzzk-fdmuzuzs-586[sygha]\n" +
            "myxcewob-qbkno-lexxi-oxqsxoobsxq-484[fmyeh]\n" +
            "pyknyegle-aylbw-amyrgle-mncpyrgmlq-470[dgahb]\n" +
            "sehheiylu-hqrryj-huqsgkyiyjyed-972[apoin]\n" +
            "iutyaskx-mxgjk-hatte-iayzuskx-ykxboik-176[ctnms]\n" +
            "uzfqdzmfuazmx-ngzzk-bgdotmeuzs-508[nslqc]\n" +
            "nglmtuex-ktfitzbgz-wrx-kxvxbobgz-397[txgba]\n" +
            "gbc-frperg-rtt-npdhvfvgvba-143[grvbf]\n" +
            "tfejldvi-xiruv-wlqqp-avccpsvre-tfekrzedvek-815[cmtyq]\n" +
            "chnylhuncihuf-wbiwifuny-lymyulwb-162[uyhil]\n" +
            "dzczkrip-xiruv-gcrjkzt-xirjj-uvgrikdvek-113[hvwun]\n" +
            "rgllk-ngzzk-emxqe-794[snuev]\n" +
            "qvbmzvibqwvit-kivlg-zmkmqdqvo-876[ivunt]\n" +
            "cqwdujys-fbqijys-whqii-ixyffydw-374[gcmut]\n" +
            "sgmtkzoi-vrgyzoi-mxgyy-cuxqynuv-852[ygimo]\n" +
            "apuut-xcjxjgvoz-pnzm-oznodib-109[ozjnp]\n" +
            "ymszqfuo-gzefmnxq-ngzzk-fqotzaxask-326[zfqag]\n" +
            "forwcoqhwjs-xszzmpsob-gsfjwqsg-610[rlhwt]\n" +
            "vcibutulxiom-vohhs-xyjulngyhn-708[dypil]\n" +
            "sgmtkzoi-inuiurgzk-jkbkruvsktz-124[qxmsb]\n" +
            "luxciuwncpy-mwupyhayl-bohn-uhufsmcm-604[uotjd]\n" +
            "jyfvnlupj-kfl-bzly-alzapun-591[lafjn]\n" +
            "dkqjcbctfqwu-hnqygt-ceswkukvkqp-622[skwtc]\n" +
            "odiih-yujbcrl-pajbb-bcxajpn-771[otehb]\n" +
            "dmbttjgjfe-gmpxfs-sftfbsdi-649[asqgv]\n" +
            "vhglnfxk-zktwx-wrx-wxiehrfxgm-345[hipzg]\n" +
            "ncjzrpytn-ojp-xlcvpetyr-379[wuezq]\n" +
            "ykhknbqh-ywjzu-ykwpejc-opknwca-238[nxhwm]\n" +
            "ide-htrgti-gpqqxi-prfjxhxixdc-609[lhtnk]\n" +
            "tinnm-qvcqczohs-hfowbwbu-272[xwhnz]\n" +
            "kmjezxodgz-wvnfzo-hvivbzhzio-603[ftdkv]\n" +
            "molgbzqfib-mixpqfz-doxpp-ildfpqfzp-237[sodim]\n" +
            "tmrszakd-okzrshb-fqzrr-kzanqzsnqx-287[zbmsl]\n" +
            "oxaflxzqfsb-mixpqfz-doxpp-abmilvjbkq-341[xbfpq]\n" +
            "zuv-ykixkz-hatte-zxgototm-150[tzkox]\n" +
            "ytu-xjhwjy-kqtbjw-xmnuunsl-515[rbjpq]\n" +
            "oqnidbshkd-bzmcx-cdrhfm-183[dbchm]\n" +
            "ipvohghykvbz-ihzrla-zavyhnl-799[gdqfx]\n" +
            "pdjqhwlf-vfdyhqjhu-kxqw-uhdftxlvlwlrq-153[zqldc]\n" +
            "kmjezxodgz-ytz-yzndbi-967[gierw]\n" +
            "cvabijtm-rmttgjmiv-xczkpiaqvo-720[imtva]\n" +
            "nglmtuex-ietlmbv-zktll-hixktmbhgl-215[nmwhy]\n" +
            "vxupkizork-sorozgxe-mxgjk-lruckx-giwaoyozout-696[lkftj]\n" +
            "mvhkvbdib-kgvnodx-bmvnn-xjiovdihzio-785[srjut]\n" +
            "eqnqthwn-rncuvke-itcuu-ewuvqogt-ugtxkeg-518[cfdqb]\n" +
            "wfruflnsl-wfggny-htsyfnsrjsy-359[fsnvr]\n" +
            "iwcjapey-ywjzu-ykwpejc-nayaerejc-576[myaou]\n" +
            "nsyjwsfyntsfq-kqtbjw-tujwfyntsx-593[swzqr]\n" +
            "vhkkhlbox-ktwbhtvmbox-utldxm-mktbgbgz-501[yfqzw]\n" +
            "qzoggwtwsr-rms-fsqswjwbu-324[grdjl]\n" +
            "cybyjqho-whqtu-sqdto-seqjydw-ixyffydw-114[yqdwf]\n" +
            "chnylhuncihuf-wfummczcyx-zfiqyl-guleyncha-136[jzbiq]\n" +
            "zuv-ykixkz-jek-ykxboiky-904[mfadn]\n" +
            "qjopwxha-sawlkjevaz-oywrajcan-dqjp-oanreyao-498[smtnr]\n" +
            "dlhwvupglk-qlssfilhu-wbyjohzpun-695[lhups]\n" +
            "udskkaxawv-usfvq-ugslafy-sfsdqkak-684[syldq]\n" +
            "cybyjqho-whqtu-tou-huiuqhsx-582[dkbhi]\n" +
            "bnqqnrhud-dff-kzanqzsnqx-833[dtzyw]\n" +
            "tipfxvezt-upv-kvtyefcfxp-685[buszt]\n" +
            "apwmeclga-cee-qcptgacq-392[yszmf]\n" +
            "zloolpfsb-pzxsbkdbo-erkq-pqloxdb-211[lykdq]\n" +
            "lzfmdshb-tmrszakd-bzmcx-bnzshmf-qdrdzqbg-313[zbdms]\n" +
            "pbafhzre-tenqr-onfxrg-ratvarrevat-403[bktor]\n" +
            "pxtihgbsxw-vahvhetmx-mxvaghehzr-631[hxvae]\n" +
            "vrurcjah-pajmn-ouxfna-jwjuhbrb-641[neckb]\n" +
            "pelbtravp-pubpbyngr-fnyrf-975[gndzm]\n" +
            "vjpwncrl-mhn-jlzdrbrcrxw-485[zopte]\n" +
            "drxevkzt-sleep-uvgcfpdvek-945[bvnau]\n" +
            "nzcczdtgp-mfyyj-dlwpd-613[yzbdk]\n" +
            "yhwooebeaz-nwxxep-pnwejejc-238[ewjno]\n" +
            "buzahisl-msvdly-dvyrzovw-643[gbeat]\n" +
            "qzlozfhmf-bzmcx-qdzbpthrhshnm-443[qosty]\n" +
            "hvbizodx-zbb-adivixdib-343[nhyjs]\n" +
            "fruurvlyh-lqwhuqdwlrqdo-gbh-vklsslqj-803[qpinz]\n" +
            "kzgwomvqk-moo-twoqabqka-642[puwmb]\n" +
            "ibghopzs-foadouwbu-pogysh-hfowbwbu-480[rktvw]\n" +
            "wsvsdkbi-qbkno-mkxni-nozkbdwoxd-588[wsytr]\n" +
            "fkqbokxqflkxi-zxkav-abpfdk-263[kfxab]\n" +
            "esyfwlau-jsttal-ogjckzgh-320[wufhe]\n" +
            "pxtihgbsxw-xzz-ehzblmbvl-319[nkcmq]\n" +
            "aflwjfslagfsd-bwddqtwsf-wfyafwwjafy-996[jkqhc]\n" +
            "hafgnoyr-enoovg-phfgbzre-freivpr-429[xwuvb]\n" +
            "jlidywncfy-xsy-xyjulngyhn-916[zotqu]\n" +
            "tfiifjzmv-gcrjkzt-xirjj-ivtvzmzex-971[cetnj]\n" +
            "bnmrtldq-fqzcd-eknvdq-rsnqzfd-885[ektcl]\n" +
            "luxciuwncpy-yaa-mniluay-266[auyci]\n" +
            "hqfxxnknji-hmthtqfyj-wjhjnansl-801[zywic]\n" +
            "votubcmf-tdbwfohfs-ivou-efwfmpqnfou-129[foubm]\n" +
            "thnulapj-jovjvshal-hjxbpzpapvu-253[ckgst]\n" +
            "xlrypetn-mldvpe-lylwjdtd-457[lsmgz]\n" +
            "ovbunmneqbhf-pnaql-svanapvat-351[alynm]\n" +
            "rdchjbtg-vgpst-rpcsn-rdpixcv-detgpixdch-765[hptsq]\n" +
            "aczupnetwp-xtwtelcj-rclop-dnlgpyrpc-sfye-dstaatyr-899[onyrk]\n" +
            "krxqjijamxdb-kjbtnc-ldbcxvna-bnaerln-511[cyist]\n" +
            "oknkvcta-itcfg-tcddkv-fgukip-700[hycuv]\n" +
            "kgjgrypw-epybc-zyqicr-mncpyrgmlq-288[ijbzo]\n" +
            "jyddc-hci-hizipstqirx-620[ervwu]\n" +
            "oknkvcta-itcfg-lgnnadgcp-ewuvqogt-ugtxkeg-544[mxano]\n" +
            "vhglnfxk-zktwx-ktuubm-ftkdxmbgz-527[zfsoa]\n" +
            "qcffcgwjs-rms-rsgwub-350[wstyq]\n" +
            "tipfxvezt-treup-tfrkzex-uvgrikdvek-321[etkrv]\n" +
            "lsyrkjkbnyec-lkcuod-nozkbdwoxd-874[kdobc]\n" +
            "ytu-xjhwjy-ojqqdgjfs-jslnsjjwnsl-437[nzycs]\n" +
            "xjgjmapg-nxvqzibzm-cpio-yzkvmohzio-967[vasue]\n" +
            "rkpqxyib-yxphbq-abpfdk-627[rvpcm]\n" +
            "qyujihctyx-wuhxs-wiuncha-mylpcwym-604[ychuw]\n" +
            "udglrdfwlyh-zhdsrqlchg-fdqgb-frqwdlqphqw-335[mtvzs]\n" +
            "bnmrtldq-fqzcd-bgnbnkzsd-qdbdhuhmf-261[ywqhu]\n" +
            "ugfkmewj-yjsvw-tskcwl-dgyaklauk-996[kwagj]\n" +
            "iqmbazulqp-qss-pqbmdfyqzf-456[ztynb]\n" +
            "awzwhofm-ufors-xszzmpsob-aofyshwbu-818[ycfar]\n" +
            "wfintfhynaj-kqtbjw-qfgtwfytwd-411[ftwjn]\n" +
            "nzydfxpc-rclop-dnlgpyrpc-sfye-opdtry-301[jwahc]\n" +
            "yaxsnlcrun-snuuhknjw-bjunb-147[zsycg]\n" +
            "cebwrpgvyr-qlr-fgbentr-923[xregt]\n" +
            "tyepcyletzylw-mldvpe-opalcexpye-457[ebvtl]\n" +
            "pbeebfvir-onfxrg-phfgbzre-freivpr-299[refbp]\n" +
            "vrurcjah-pajmn-npp-mnbrpw-953[hwayg]\n" +
            "kzeed-wfggny-fhvznxnynts-775[bmnjw]\n" +
            "tcorcikpi-ejqeqncvg-wugt-vguvkpi-336[cgive]\n" +
            "mbiyqoxsm-mkxni-mykdsxq-crszzsxq-224[mgtde]\n" +
            "bwx-amkzmb-kpwkwtibm-uizsmbqvo-616[zywgf]\n" +
            "qfkkj-mldvpe-afcnsldtyr-925[dfkla]\n" +
            "qzoggwtwsr-qobrm-qcbhowbasbh-974[vdxcz]\n" +
            "jshzzpmplk-yhiipa-bzly-alzapun-279[pzalh]\n" +
            "zotts-vumeyn-mniluay-344[moyzn]\n" +
            "eza-dpncpe-nlyoj-nzletyr-dezclrp-249[rmlfn]\n" +
            "rdadguja-ytaanqtpc-sthxvc-895[atcdg]\n" +
            "bkzrrhehdc-rbzudmfdq-gtms-trdq-sdrshmf-729[vnaxm]\n" +
            "wifilzof-willimcpy-vohhs-mniluay-864[rxqlk]\n" +
            "dzczkrip-xiruv-zekvierkzferc-gcrjkzt-xirjj-uvmvcfgdvek-529[mautj]\n" +
            "hafgnoyr-pelbtravp-onfxrg-erprvivat-741[xvymo]\n" +
            "xmrrq-tskcwl-ksdwk-268[uzesm]\n" +
            "htsxzrjw-lwfij-kqtbjw-yjhmstqtld-801[jtwhl]\n" +
            "avw-zljyla-msvdly-yljlpcpun-435[cedbt]\n" +
            "zovldbkfz-zxkav-zlxqfkd-xznrfpfqflk-185[fzklx]\n" +
            "gvcskirmg-gerhc-wxsveki-308[gceik]\n" +
            "gpsxdprixkt-snt-ldgzhwde-167[zbkqn]\n" +
            "dkqjcbctfqwu-eqttqukxg-tcddkv-fgxgnqrogpv-466[eygsw]\n" +
            "thnulapj-kfl-lunpullypun-643[zyucd]\n" +
            "vjpwncrl-kjbtnc-mnenuxyvnwc-641[ncjvw]\n" +
            "sgmtkzoi-atyzghrk-hgyqkz-xkykgxin-488[jyivp]\n" +
            "zvyvgnel-tenqr-rtt-erprvivat-299[dvxye]\n" +
            "vehmsegxmzi-gpewwmjmih-ikk-hizipstqirx-698[xtwpi]\n" +
            "sno-rdbqds-lhkhszqx-fqzcd-bzmcx-bnzshmf-lzqjdshmf-287[twsqz]\n" +
            "jqwpihizlwca-zijjqb-lmdmtwxumvb-694[vhibn]\n" +
            "fodvvlilhg-fkrfrodwh-wudlqlqj-595[ldfho]\n" +
            "nuatmlmdpage-qss-efadmsq-248[kzgwp]\n" +
            "sehheiylu-isqludwuh-xkdj-efuhqjyedi-894[ehudi]\n" +
            "bpvctixr-hrpktcvtg-wjci-itrwcdadvn-843[gjtsq]\n" +
            "sehheiylu-zubboruqd-tufbeocudj-556[ubedh]\n" +
            "pinovwgz-wvnfzo-gvwjmvojmt-291[spxor]\n" +
            "vhkkhlbox-lvtoxgzxk-angm-kxlxtkva-163[kxlva]\n" +
            "shmml-pubpbyngr-qrcyblzrag-273[xycpv]\n" +
            "lahxpnwrl-mhn-anjlzdrbrcrxw-303[rlnah]\n" +
            "vhglnfxk-zktwx-cxeeruxtg-wxlbzg-137[xhfpw]\n" +
            "vagreangvbany-pnaql-svanapvat-845[anvgp]\n" +
            "rzvkjiduzy-xviyt-xjvodib-gvwjmvojmt-655[ghens]\n" +
            "ygcrqpkbgf-ejqeqncvg-rwtejcukpi-388[xjiod]\n" +
            "wlqqp-kfg-jvtivk-gcrjkzt-xirjj-tfekrzedvek-997[kjert]\n" +
            "ucynmlgxcb-djmucp-asqrmkcp-qcptgac-366[wqhry]\n" +
            "otzkxtgzoutgr-lruckx-ygrky-202[gtxfy]\n" +
            "houngfgxjuay-hgyqkz-ktmotkkxotm-748[kgoth]\n" +
            "bnknqetk-bzmcx-kzanqzsnqx-989[nkqzb]\n" +
            "vrurcjah-pajmn-mhn-lxwcjrwvnwc-251[jaxic]\n" +
            "sbqiiyvyut-tou-efuhqjyedi-894[qksiu]\n" +
            "oaddaeuhq-bxmefuo-sdmee-efadmsq-716[inakb]\n" +
            "vkrhzxgbv-wrx-phkdlahi-215[hkrvx]\n" +
            "xcitgcpixdcpa-tvv-gtprfjxhxixdc-999[jemot]\n" +
            "vehmsegxmzi-veffmx-wxsveki-594[bhgyq]\n" +
            "laffe-vrgyzoi-mxgyy-uvkxgzouty-124[ygfou]\n" +
            "qzlozfhmf-dff-zbpthrhshnm-651[mkbun]\n" +
            "xmrrq-wyy-dgyaklauk-138[yakrd]\n" +
            "cvabijtm-kivlg-kwvbiqvumvb-980[vbikm]\n" +
            "hmsdqmzshnmzk-azrjds-sdbgmnknfx-989[lvuhe]\n" +
            "vdzonmhydc-dff-sqzhmhmf-625[enwxv]\n" +
            "pwcvonofrcig-xszzmpsob-gvwddwbu-740[hlvts]\n" +
            "lnkfaypeha-oywrajcan-dqjp-ajcejaanejc-732[ajcen]\n" +
            "ugfkmewj-yjsvw-xdgowj-sfsdqkak-606[xnjmw]\n" +
            "bqvvu-ywjzu-ykwpejc-opknwca-940[zywlu]\n" +
            "vdzonmhydc-azrjds-zbpthrhshnm-131[dnjcy]\n" +
            "vqr-ugetgv-fag-nqikuvkeu-544[sydvo]\n" +
            "vjpwncrl-bljenwpna-qdwc-anbnjalq-693[najlw]\n" +
            "pbybeshy-fpniratre-uhag-ybtvfgvpf-481[msyze]\n" +
            "yhwooebeaz-ynukcajey-xwogap-hkceopeyo-212[eoyac]\n" +
            "yrwxefpi-hci-xiglrspskc-438[zytvr]\n" +
            "ujoon-tvv-hwxeexcv-427[ukymv]\n" +
            "nsyjwsfyntsfq-wfggny-ijajqturjsy-957[pboys]\n" +
            "zotts-vumeyn-lywycpcha-838[ujstb]\n" +
            "upq-tfdsfu-dipdpmbuf-tijqqjoh-129[bluat]\n" +
            "oazegyqd-sdmpq-eomhqzsqd-tgzf-emxqe-170[yopas]\n" +
            "ktfitzbgz-vtgwr-vhtmbgz-kxtvjnblbmbhg-735[trlen]\n" +
            "rdggdhxkt-uadltg-jhtg-ithixcv-401[yixqs]\n" +
            "qfkkj-clmmte-opdtry-639[kmtcd]\n" +
            "ibghopzs-pibbm-rsdofhasbh-220[sutpe]\n" +
            "eqnqthwn-ecpfa-eqcvkpi-ocpcigogpv-596[qmfhp]\n" +
            "hwdtljsnh-hfsid-htfynsl-zxjw-yjxynsl-931[nmojg]\n" +
            "ykjoqian-cnwza-xwogap-paydjkhkcu-472[akcjn]\n" +
            "xlrypetn-nlyoj-nzletyr-opawzjxpye-197[fevmd]\n" +
            "lzfmdshb-azrjds-kzanqzsnqx-755[zftaw]\n" +
            "apuut-xjgjmapg-xcjxjgvoz-mznzvmxc-577[gnzmx]\n" +
            "hqcfqwydw-hqrryj-ijehqwu-608[qhwjr]\n" +
            "lnkfaypeha-ejpanjwpekjwh-zua-pnwejejc-368[vwqni]\n" +
            "bjfutsneji-jll-xmnuunsl-853[ozghw]\n" +
            "muqfedyput-ydjuhdqjyedqb-vbemuh-tulubefcudj-712[lqaik]\n" +
            "willimcpy-jlidywncfy-wuhxs-guhuaygyhn-214[itesx]\n" +
            "rkpqxyib-zixppfcfba-ciltbo-rpbo-qbpqfkd-887[bpfiq]\n" +
            "eadalsjq-yjsvw-usfvq-ugslafy-ljsafafy-788[asfjl]\n" +
            "gvaaz-kfmmzcfbo-qvsdibtjoh-103[ankyj]\n" +
            "shmml-pnaql-pbngvat-phfgbzre-freivpr-403[mofch]\n" +
            "shmml-cynfgvp-tenff-hfre-grfgvat-273[fgehm]\n" +
            "ibghopzs-tzcksf-difqvogwbu-870[bxgar]\n" +
            "ymszqfuo-rxaiqd-dqmocgueufuaz-196[xmwtu]\n" +
            "egdytrixat-uadltg-hidgpvt-115[mslhc]\n" +
            "rkpqxyib-oxyyfq-abpfdk-445[qlrak]\n" +
            "irdgrxzex-lejkrscv-irsszk-jkfirxv-191[izgye]\n" +
            "jsvagsulanw-hdsklau-yjskk-sfsdqkak-112[skadj]\n" +
            "zgmfyxypbmsq-hcjjwzcyl-bctcjmnkclr-600[cjmyb]\n" +
            "jqwpihizlwca-ntwemz-uizsmbqvo-616[gijkn]\n" +
            "guahyncw-wuhxs-wiuncha-nywbhifias-994[cipny]\n" +
            "xgsvgmotm-kmm-ktmotkkxotm-436[ywzib]\n" +
            "ykhknbqh-ywjzu-zalwnpiajp-186[csbmn]\n" +
            "udpsdjlqj-fkrfrodwh-zrunvkrs-283[rdfjk]\n" +
            "yuxufmdk-sdmpq-eomhqzsqd-tgzf-mocgueufuaz-820[mskbl]\n" +
            "sbqiiyvyut-isqludwuh-xkdj-qsgkyiyjyed-530[ndmuc]\n" +
            "mbiyqoxsm-oqq-crszzsxq-952[gxqmn]\n" +
            "rwcnawjcrxwju-lqxlxujcn-cnlqwxuxph-849[zqekt]\n" +
            "kpvgtpcvkqpcn-fag-ugtxkegu-986[qpyuj]\n" +
            "dfcxsqhwzs-qzoggwtwsr-qvcqczohs-fsoqeiwgwhwcb-714[lgtfc]\n" +
            "ojk-nzxmzo-xviyt-xjvodib-omvdidib-265[iodvx]\n" +
            "wbhsfbohwcboz-qobrm-zcuwghwqg-298[bwhoc]\n" +
            "shoewudys-tou-ixyffydw-478[uszty]\n";
}