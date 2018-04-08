function convertCapFee(n) {
    if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n)) {
        return "数据非法";
    }
    var unit = "千百拾亿千百拾万千百拾元角分", 
        str = "";
        n += "00";

    var p = n.indexOf('.');

    if (p >= 0) {
        n = n.substring(0, p) + n.substr(p+1, 2);
    }
    unit = unit.substr(unit.length - n.length);
    for (var i=0; i < n.length; i++) {
        str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
    }
    return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
}

var chnNumChar = ["零","一","二","三","四","五","六","七","八","九"];
var chnUnitSection = ["","万","亿","万亿","亿亿"];
var chnUnitChar = ["","十","百","千"];

function NumberToChinese(num){
  var unitPos = 0;
  var strIns = '', chnStr = '';
  var needZero = false;
 
  if(num === 0){
    return chnNumChar[0];
  }
 
  while(num > 0){
    var section = num % 10000;
    if(needZero){
      chnStr = chnNumChar[0] + chnStr;
    }
    strIns = SectionToChinese(section);
    strIns += (section !== 0) ? chnUnitSection[unitPos] : chnUnitSection[0];
    chnStr = strIns + chnStr;
    needZero = (section < 1000) && (section > 0);
    num = Math.floor(num / 10000);
    unitPos++;
  }
 
  return chnStr;
}

function SectionToChinese(section){
  var strIns = '', chnStr = '';
  var unitPos = 0;
  var zero = true;
  while(section > 0){
    var v = section % 10;
    if(v === 0){
      if(!zero){
        zero = true;
        chnStr = chnNumChar[v] + chnStr;
      }
    }else{
      zero = false;
      strIns = chnNumChar[v];
      strIns += chnUnitChar[unitPos];
      chnStr = strIns + chnStr;
    }
    unitPos++;
    section = Math.floor(section / 10);
  }
  return chnStr;
}

var chnNumChar1 = {
  零:0,
  一:1,
  二:2,
  三:3,
  四:4,
  五:5,
  六:6,
  七:7,
  八:8,
  九:9
};

var chnNameValue1 = {
  十:{value:10, secUnit:false},
  百:{value:100, secUnit:false},
  千:{value:1000, secUnit:false},
  万:{value:10000, secUnit:true},
  亿:{value:100000000, secUnit:true}
}

function ChineseToNumber(chnStr){
  var rtn = 0;
  var section = 0;
  var number = 0;
  var secUnit = false;
  var str = chnStr.split('');
 
  for(var i = 0; i < str.length; i++){
    var num = chnNumChar1[str[i]];
    if(typeof num !== 'undefined'){
      number = num;
      if(i === str.length - 1){
        section += number;
      }
    }else{
      var unit = chnNameValue1[str[i]].value;
      secUnit = chnNameValue1[str[i]].secUnit;
      if(secUnit){
        section = (section + number) * unit;
        rtn += section;
        section = 0;
      }else{
        section += (number * unit);
      }
      number = 0;
    }
  }
  return rtn + section;
}