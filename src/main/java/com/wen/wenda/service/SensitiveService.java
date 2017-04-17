package com.wen.wenda.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wen on 2017/4/12.
 */
//敏感词过滤服务
@Service
public class SensitiveService implements InitializingBean{

    private Logger logger= LoggerFactory.getLogger(SensitiveService.class);

    //读取敏感词文件
    @Override
    public void afterPropertiesSet() {

        try {
            InputStream is = Thread.currentThread().getContextClassLoader().
                    getResourceAsStream("sensitiveWords.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String lineTxt = null;
            while ((lineTxt = br.readLine()) != null) {
                addSensitiveWords(lineTxt);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("敏感词汇文件解析错误");
        }


    }

    public String sensitiveFilter(String text){
        if(StringUtils.isBlank(text)){
            return text;
        }

        String replace="***";
        TrieNode tempNode=rootNode;
        int begin=0;
        int position=0;
        StringBuilder response=new StringBuilder();
        while(position<text.length()){
            Character c=text.charAt(position);

            TrieNode node=tempNode.getNode(c);
            if(node==null){
                response.append(text.charAt(begin));

                position++;
                begin=position;
                tempNode=rootNode;
            }else if(node.isEnd()){
                tempNode=rootNode;
                position++;
                begin=position;
                response.append(replace);
            }else if(!node.isEnd()){
                tempNode=node;
                position++;
            }
        }

        return response.toString();
    }

    //将敏感词添加到树中
    private void addSensitiveWords(String lineTxt){
        TrieNode tempNode=rootNode;
        for(int i=0;i<lineTxt.length();i++){
            Character c=lineTxt.charAt(i);

            TrieNode trieNode=tempNode.getNode(c);
            if(trieNode==null){
                trieNode=new TrieNode();
                tempNode.addNode(c,trieNode);
            }

            tempNode=trieNode;

            if(i==lineTxt.length()-1){
                tempNode.setEnd(true);
            }

        }

    }

    //节点类
    private class  TrieNode{
        private boolean end=false;

        private Map<Character,TrieNode> subNodes=new HashMap<>();

        public void addNode(Character key, TrieNode node){
            subNodes.put(key,node);
        }

        public TrieNode getNode(Character key){
           return subNodes.get(key);
        }

        public void setEnd(boolean end) {
            this.end = end;
        }

        public boolean isEnd(){return end;}
    }

    //树的根节点
    private TrieNode rootNode=new TrieNode();

}
