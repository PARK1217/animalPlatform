package com.example.animalplatform.utils;

import com.example.animalplatform.log.JLog;

public class AgentUtil{
    public String gAgent = null;
    public AgentUtil(String agent) {
        this.gAgent = agent.toLowerCase();
    }


    public boolean CheckAgent(){
        String agentName = getClientName();
        JLog.logd("Agent Name : " + agentName);
        if(!"Android".equals(agentName)
                && !"Iphone".equals(agentName)
                && !"Ipad".equals(agentName)
                && !"Safari".equals(agentName)){
            return false;
        }
        return true;
    }

    public String getClientName() {
        if (this.gAgent.indexOf("android") != -1) return "Android";
        if (this.gAgent.indexOf("iphone") != -1) return "Iphone";
        if (this.gAgent.indexOf("ipad") != -1) return "Ipad";
        if (this.gAgent.indexOf("chrome") != -1) return "Chrome";
        if (this.gAgent.indexOf("opera") != -1) return "Opera";
        if (this.gAgent.indexOf("staroffice") != -1) return "Star Office";
        if (this.gAgent.indexOf("webtv") != -1) return "WebTV";
        if (this.gAgent.indexOf("beonex") != -1) return "Beonex";
        if (this.gAgent.indexOf("chimera") != -1) return "Chimera";
        if (this.gAgent.indexOf("netpositive") != -1) return "NetPositive";
        if (this.gAgent.indexOf("phoenix") != -1) return "Phoenix";
        if (this.gAgent.indexOf("firefox") != -1) return "Firefox";
        if (this.gAgent.indexOf("safari") != -1) return "Safari";
        if (this.gAgent.indexOf("skipstone") != -1) return "SkipStone";
        if (this.gAgent.indexOf("msie") != -1) return "Internet Explorer";
        if (this.gAgent.indexOf("netscape") != -1) return "Netscape";
        if (this.gAgent.indexOf("mozilla/5.0") != -1) return "Mozilla";
        else {return null;}
    }
}