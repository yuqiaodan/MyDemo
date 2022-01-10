package com.yuqiaodan.mydemo.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qiaodan on 2022/1/10
 * Description:
 */
@Entity
public class Idiom {

    @Id(autoincrement = true)
    private Long id;
    //成语
    private String word;
    //拼音 读法
    private String pinyin;
    //出处
    private String derivation;
    //使用例子
    private String example;
    //解释
    private String explanation;
    //缩写
    private String abbreviation;
    @Generated(hash = 223455123)
    public Idiom(Long id, String word, String pinyin, String derivation,
            String example, String explanation, String abbreviation) {
        this.id = id;
        this.word = word;
        this.pinyin = pinyin;
        this.derivation = derivation;
        this.example = example;
        this.explanation = explanation;
        this.abbreviation = abbreviation;
    }
    @Generated(hash = 1459594445)
    public Idiom() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWord() {
        return this.word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getPinyin() {
        return this.pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    public String getDerivation() {
        return this.derivation;
    }
    public void setDerivation(String derivation) {
        this.derivation = derivation;
    }
    public String getExample() {
        return this.example;
    }
    public void setExample(String example) {
        this.example = example;
    }
    public String getExplanation() {
        return this.explanation;
    }
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    public String getAbbreviation() {
        return this.abbreviation;
    }
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
