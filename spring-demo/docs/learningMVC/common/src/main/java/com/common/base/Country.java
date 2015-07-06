package com.springdemo.learningMVC.common.src.main.java.com.common.base;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public final class Country {

    /** 用于表示澳大利亚的有用常量。 */
    public static final String AUSTRALIA = "AU";

    /** 用于表示巴西的有用常量。 */
    public static final String BRAZIL = "BR";

    /** 用于表示加拿大的有用常量。 */
    public static final String CANADA = "CA";

    /** 用于表示中国的有用常量。 */
    public static final String CHINA = "CN";

    /** 用于表示德国的有用常量。 */
    public static final String GERMANY = "DE";

    /** 用于表示法国的有用常量。 */
    public static final String FRANCE = "FR";

    /** 用于表示香港的有用常量。 */
    public static final String HONG_KONG = "HK";

    /** 用于表示印度的有用常量。 */
    public static final String INDIA = "IN";

    /** 用于表示日本的有用常量。 */
    public static final String JAPAN = "JP";

    /** 用于表示韩国的有用常量。 */
    public static final String KOREA = "KR";

    /** 用于表示澳门的有用常量。 */
    public static final String MACAO = "MO";

    /** 用于表示马来西亚的有用常量。 */
    public static final String MALAYSIA = "MY";

    /** 用于表示新西兰的有用常量。 */
    public static final String NEW_ZEA_LAND = "NZ";

    /** 用于表示朝鲜的有用常量。 */
    public static final String NORTH_KOREA = "KP";

    /** 用于表示新加坡的有用常量。 */
    public static final String SINGAPORE = "SG";

    /** 用于表示台湾的有用常量。 */
    public static final String TAIWAN = "TW";

    /** 用于表示英国的有用常量。 */
    public static final String UK = "GB";

    /** 用于表示美国的有用常量。 */
    public static final String US = "US";

    private static final ImmutableMap<String, String> countries;

    static {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("AO", "安哥拉");
        builder.put("AF", "阿富汗");
        builder.put("AL", "阿尔巴尼亚");
        builder.put("DZ", "阿尔及利亚");
        builder.put("AD", "安道尔共和国");
        builder.put("AI", "安圭拉岛");
        builder.put("AG", "安提瓜和巴布达");
        builder.put("AR", "阿根廷");
        builder.put("AM", "亚美尼亚");
        builder.put(AUSTRALIA, "澳大利亚");
        builder.put("AT", "奥地利");
        builder.put("AZ", "阿塞拜疆");
        builder.put("BS", "巴哈马");
        builder.put("BH", "巴林");
        builder.put("BD", "孟加拉国");
        builder.put("BB", "巴巴多斯");
        builder.put("BY", "白俄罗斯");
        builder.put("BE", "比利时");
        builder.put("BZ", "伯利兹");
        builder.put("BJ", "贝宁");
        builder.put("BM", "百慕大群岛");
        builder.put("BO", "玻利维亚");
        builder.put("BW", "博茨瓦纳");
        builder.put(BRAZIL, "巴西");
        builder.put("BN", "文莱");
        builder.put("BG", "保加利亚");
        builder.put("BF", "布基纳法索");
        builder.put("MM", "缅甸");
        builder.put("BI", "布隆迪");
        builder.put("CM", "喀麦隆");
        builder.put(CANADA, "加拿大");
        builder.put("CF", "中非共和国");
        builder.put("TD", "乍得");
        builder.put("CL", "智利");
        builder.put(CHINA, "中国");
        builder.put("CO", "哥伦比亚");
        builder.put("CG", "刚果");
        builder.put("CK", "库克群岛");
        builder.put("CR", "哥斯达黎加");
        builder.put("CU", "古巴");
        builder.put("CY", "塞浦路斯");
        builder.put("CZ", "捷克");
        builder.put("DK", "丹麦");
        builder.put("DJ", "吉布提");
        builder.put("DO", "多米尼加共和国");
        builder.put("EC", "厄瓜多尔");
        builder.put("EG", "埃及");
        builder.put("SV", "萨尔瓦多");
        builder.put("EE", "爱沙尼亚");
        builder.put("ET", "埃塞俄比亚");
        builder.put("FJ", "斐济");
        builder.put("FI", "芬兰");
        builder.put(FRANCE, "法国");
        builder.put("GF", "法属圭亚那");
        builder.put("GA", "加蓬");
        builder.put("GM", "冈比亚");
        builder.put("GE", "格鲁吉亚");
        builder.put(GERMANY, "德国");
        builder.put("GH", "加纳");
        builder.put("GI", "直布罗陀");
        builder.put("GR", "希腊");
        builder.put("GD", "格林纳达");
        builder.put("GU", "关岛");
        builder.put("GT", "危地马拉");
        builder.put("GN", "几内亚");
        builder.put("GY", "圭亚那");
        builder.put("HT", "海地");
        builder.put("HN", "洪都拉斯");
        builder.put(HONG_KONG, "香港");
        builder.put("HU", "匈牙利");
        builder.put("IS", "冰岛");
        builder.put(INDIA, "印度");
        builder.put("ID", "印度尼西亚");
        builder.put("IR", "伊朗");
        builder.put("IQ", "伊拉克");
        builder.put("IE", "爱尔兰");
        builder.put("IL", "以色列");
        builder.put("IT", "意大利");
        builder.put("JM", "牙买加");
        builder.put(JAPAN, "日本");
        builder.put("JO", "约旦");
        builder.put("KH", "柬埔寨");
        builder.put("KZ", "哈萨克斯坦");
        builder.put("KE", "肯尼亚");
        builder.put(KOREA, "韩国");
        builder.put("KW", "科威特");
        builder.put("KG", "吉尔吉斯坦");
        builder.put("LA", "老挝");
        builder.put("LV", "拉脱维亚");
        builder.put("LB", "黎巴嫩");
        builder.put("LS", "莱索托");
        builder.put("LR", "利比里亚");
        builder.put("LY", "利比亚");
        builder.put("LI", "列支敦士登");
        builder.put("LT", "立陶宛");
        builder.put("LU", "卢森堡");
        builder.put(MACAO, "澳门");
        builder.put("MG", "马达加斯加");
        builder.put("MW", "马拉维");
        builder.put(MALAYSIA, "马来西亚");
        builder.put("MV", "马尔代夫");
        builder.put("ML", "马里");
        builder.put("MT", "马耳他");
        builder.put("MU", "毛里求斯");
        builder.put("MX", "墨西哥");
        builder.put("MD", "摩尔多瓦");
        builder.put("MC", "摩纳哥");
        builder.put("MN", "蒙古");
        builder.put("MS", "蒙特塞拉特岛");
        builder.put("MA", "摩洛哥");
        builder.put("MZ", "莫桑比克");
        builder.put("NA", "纳米比亚");
        builder.put("NR", "瑙鲁");
        builder.put("NP", "尼泊尔");
        builder.put("NL", "荷兰");
        builder.put(NEW_ZEA_LAND, "新西兰");
        builder.put("NI", "尼加拉瓜");
        builder.put("NE", "尼日尔");
        builder.put("NG", "尼日利亚");
        builder.put(NORTH_KOREA, "朝鲜");
        builder.put("NO", "挪威");
        builder.put("OM", "阿曼");
        builder.put("PK", "巴基斯坦");
        builder.put("PA", "巴拿马");
        builder.put("PG", "巴布亚新几内亚");
        builder.put("PY", "巴拉圭");
        builder.put("PE", "秘鲁");
        builder.put("PH", "菲律宾");
        builder.put("PL", "波兰");
        builder.put("PF", "法属玻利尼西亚");
        builder.put("PT", "葡萄牙");
        builder.put("PR", "波多黎各");
        builder.put("QA", "卡塔尔");
        builder.put("RO", "罗马尼亚");
        builder.put("RU", "俄罗斯");
        builder.put("LC", "圣卢西亚");
        builder.put("VC", "圣文森特岛");
        builder.put("SM", "圣马力诺");
        builder.put("ST", "圣多美和普林西比");
        builder.put("SA", "沙特阿拉伯");
        builder.put("SN", "塞内加尔");
        builder.put("SC", "塞舌尔");
        builder.put("SL", "塞拉利昂");
        builder.put(SINGAPORE, "新加坡");
        builder.put("SK", "斯洛伐克");
        builder.put("SI", "斯洛文尼亚");
        builder.put("SB", "所罗门群岛");
        builder.put("SO", "索马里");
        builder.put("ZA", "南非");
        builder.put("ES", "西班牙");
        builder.put("LK", "斯里兰卡");
        builder.put("LC", "圣卢西亚");
        builder.put("VC", "圣文森特");
        builder.put("SD", "苏丹");
        builder.put("SR", "苏里南");
        builder.put("SZ", "斯威士兰");
        builder.put("SE", "瑞典");
        builder.put("CH", "瑞士");
        builder.put("SY", "叙利亚");
        builder.put(TAIWAN, "台湾省");
        builder.put("TJ", "塔吉克斯坦");
        builder.put("TZ", "坦桑尼亚");
        builder.put("TH", "泰国");
        builder.put("TG", "多哥");
        builder.put("TO", "汤加");
        builder.put("TT", "特立尼达和多巴哥");
        builder.put("TN", "突尼斯");
        builder.put("TR", "土耳其");
        builder.put("TM", "土库曼斯坦");
        builder.put("UG", "乌干达");
        builder.put("UA", "乌克兰");
        builder.put("AE", "阿拉伯联合酋长国");
        builder.put(UK, "英国");
        builder.put(US, "美国");
        builder.put("UY", "乌拉圭");
        builder.put("UZ", "乌兹别克斯坦");
        builder.put("VE", "委内瑞拉");
        builder.put("VN", "越南");
        builder.put("YE", "也门");
        builder.put("YU", "南斯拉夫");
        builder.put("ZW", "津巴布韦");
        builder.put("ZR", "扎伊尔");
        builder.put("ZM", "赞比亚");
        countries = builder.build();
    }

    /**
     * 返回指定国家代码的国家名称。如果给定的代码不存在，则返回 {@code null}。
     *
     * <pre>
     * Country.getName(Country.CN) = &quot;中国&quot;
     * </pre>
     *
     * @param code 国家代码。
     * @return 对应的国家名称。
     */
    public static String getName(String code) {
        if (code == null || code.length() < 2) {
            return null;
        }
        return countries.get(code.toUpperCase());
    }

    /**
     * 返回所有国家代码的集合。
     *
     * @return 所有国家代码的集合。
     */
    public static Map<String, String> asMap() {
        return countries;
    }

    /**
     * 返回所有的国家代码列表。
     *
     * @return 所有的国家代码列表。
     */
    public static List<String> getCountriesCode() {
        return ImmutableList.copyOf(countries.keySet());
    }
}