<template>
  <div style="background-color: #eca1a6;">
    <h1 class="header"><b><i>Calculator</i></b></h1>
  </div>
    <div id="app" class="main">
    <div>
      <table>
        <tr>
          <div class="divview ">
            <h4>{{ prevData }}</h4>
            <h1>{{ currentData }}</h1>
          </div>
        </tr>
        <tr>
          <div>
            <button @click="percentage()" :disabled="errorExists" class="Button2">%</button>
            <button style="width: 143px;" @click="clearAll()" class="Button2">CE</button>
            <button @click="del()" class="Button2">DEL</button>
          </div>
        </tr>
        <tr>
          <div>
            <button @click="inverse()" :disabled="errorExists" class="Button2">1/𝑥</button>
            <button @click="square()" :disabled="errorExists" class="Button2">𝑥^2</button>
            <button @click="root()" :disabled="errorExists" class="Button2">√𝑥</button>
            <button @click="appendOp('÷')" :disabled="errorExists" class="Button2">÷</button>
          </div>
        </tr>
        <tr>
          <div>
            <button @click="appendNum('7')" class="Button1">7</button>
            <button @click="appendNum('8')" class="Button1">8</button>
            <button @click="appendNum('9')" class="Button1">9</button>
            <button @click="appendOp('x')" :disabled="errorExists" class="Button2">x</button>
          </div>
        </tr>
        <tr>
          <div>
            <button @click="appendNum('4')" class="Button1">4</button>
            <button @click="appendNum('5')" class="Button1">5</button>
            <button @click="appendNum('6')" class="Button1">6</button>
            <button @click="appendOp('-')" :disabled="errorExists" class="Button2">-</button>
          </div>
        </tr>
        <tr>
          <div>
            <button @click="appendNum('1')" class="Button1">1</button>
            <button @click="appendNum('2')" class="Button1">2</button>
            <button @click="appendNum('3')" class="Button1">3</button>
            <button @click="appendOp('+')" :disabled="errorExists" class="Button2">+</button>
          </div>
        </tr>
        <tr>
          <div>
            <button @click="sign()" :disabled="errorExists" class="Button2">+/-</button>
            <button @click="appendNum('0')" class="Button2">0</button>
            <button @click="point()" :disabled="errorExists" class="Button2">.</button>
            <button @click="equal()" :disabled="errorExists || evaluated" class="Button2">=</button>
          </div>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      firstOp: '',
      secondOp: '',
      operator: '',
      flag: false,
      equalFlag: false,
      operatorLast:false,
      unaryOp: '',
      negate: false,
      unaryHistory: '',
      evaluated: false,
      errorExists: false,
      currentData: '0',
      prevData: '0',
    }
  },
  methods: {
    async appendOp(num) {
      if(this.errorExists){
        this.errorExists = false;
        this.clearAll();
      }
      if(this.evaluated){
        this.prevData = '0';
        this.firstOp = '';
        this.secondOp = '';
        this.operator = '';
        this.unaryHistory = '';
        this.unaryOp = '';
        this.flag = false;
        this.equalFlag  = false;
        this.evaluated = false;
        this.errorExists = false;
        this.operatorLast = false;
      }
      this.currentData = this.currentData.toString();
      if(this.currentData.charAt((this.currentData.length - 1)) === '.'){
        this.currentData = this.currentData.slice(0,-1);
      }
      this.flag = true;
      if ((this.operatorLast&& !this.negate) || this.equalFlag) {
        this.prevData = this.prevData.slice(0, -1) + num;
        this.operator = num;
      }else{
        if (this.firstOp === '') {
          this.firstOp = this.currentData;
          if(this.unaryHistory === ''){
            this.prevData = this.currentData + num;
          }else{
            this.prevData += num;
            this.unaryHistory = '';
          }
        } else {
          this.secondOp = this.currentData;
          if(this.unaryHistory === ''){
            this.prevData += this.currentData + num;
          }else{
            this.prevData += num;
            this.unaryHistory = '';
          }
        }
        if (this.firstOp !== '' && this.operator !== '' && this.secondOp !== '') {
          await this.operations();
        }
        console.log(this.operatorLast);
        this.operator = num;
      }
      this.operatorLast = true;
      this.equalFlag = false;
      this.negate = false;
    },

    appendNum(num){
      if(this.unaryHistory !== ''){
        this.deleteUnaryHistory();
        this.unaryHistory = '';
        this.currentData = '';
      }
      if(this.errorExists){
        this.errorExists = false;
        this.clearAll();
      }
      if(this.evaluated){
        this.evaluated = false;
        this.equalFlag = false;
        this.clearAll();
      }
      this.operatorLast = false;
      if(this.currentData === '0' || this.flag || this.equalFlag){
        this.currentData = '';
      }
      this.currentData += num;
      this.flag = false;
      this.equalFlag = false;
    },

    point(){
      if(this.evaluated){
        this.evaluated = false;
        this.clearAll();
      }
      if(!(this.currentData.includes("."))){
        this.currentData = this.currentData + ".";
      }
    },

    equal(){
      if(this.operator === ''){
        this.firstOp = this.currentData;
        if(this.unaryHistory ===  ''){
          this.prevData = this.currentData +"=";
        }else{
         this.prevData += "=";
        }
      }else{
        this.secondOp = this.currentData ;
        if(this.unaryHistory === ''){
          this.prevData = this.prevData+this.currentData + "=";
        }else{
          this.prevData = this.prevData+ "=";
        }
        this.operations();
      }
      this.evaluated = true;
      this.equalFlag = true;
    },

    square(){
      if(this.evaluated){
        this.prevData = '0';
        this.firstOp = '';
        this.secondOp = '';
        this.operator = '';
        this.unaryHistory = '';
        this.unaryOp = '';
        this.flag = false;
        this.equalFlag  = false;
        this.evaluated = false;
        this.errorExists = false;
        this.operatorLast = false;
      }
      this.unaryOp = "square";
      if(this.unaryHistory === ''){
        this.unaryHistory = "sqr("+this.currentData+")";
        if(this.prevData === '0'){
          this.prevData = this.unaryHistory;
        }else{
          this.prevData = this.prevData + this.unaryHistory;
        }
      }else{
        this.deleteUnaryHistory();
        this.unaryHistory = "sqrt("+this.unaryHistory+")";
        if(this.prevData === '0'){
          this.prevData = this.unaryHistory;
        }else{
          this.prevData = this.prevData + this.unaryHistory;
        }
      }
      this.unaryOperations();
    },

    root(){
      if(this.evaluated){
        this.prevData = '0';
        this.firstOp = '';
        this.secondOp = '';
        this.operator = '';
        this.unaryHistory = '';
        this.unaryOp = '';
        this.flag = false;
        this.equalFlag  = false;
        this.evaluated = false;
        this.errorExists = false;
        this.operatorLast = false;
      }
      this.unaryOp = "root";
      if(this.unaryHistory === ''){
        this.unaryHistory = "√("+this.currentData+")";
        if(this.prevData === '0'){
          this.prevData = this.unaryHistory;
        }else{
          this.prevData = this.prevData + this.unaryHistory;
        }
      }else{
        this.deleteUnaryHistory();
        this.unaryHistory = "√("+this.unaryHistory+")";
        if(this.prevData === '0'){
          this.prevData = this.unaryHistory;
        }else{
          this.prevData = this.prevData + this.unaryHistory;
        }
      }
      this.unaryOperations();
    },

    inverse(){
      if(this.evaluated){
        this.prevData = '0';
        this.firstOp = '';
        this.secondOp = '';
        this.operator = '';
        this.unaryHistory = '';
        this.unaryOp = '';
        this.flag = false;
        this.equalFlag  = false;
        this.evaluated = false;
        this.errorExists = false;
        this.operatorLast = false;
      }
      this.unaryOp = "inverse";
      if(this.unaryHistory === ''){
        this.unaryHistory = "1/("+this.currentData+")";
        if(this.prevData === '0'){
          this.prevData = this.unaryHistory;
        }else{
          this.prevData = this.prevData + this.unaryHistory;
        }
      }else{
        this.deleteUnaryHistory();
        this.unaryHistory = "1/("+this.unaryHistory+")";
        if(this.prevData === '0'){
          this.prevData = this.unaryHistory;
        }else{
          this.prevData = this.prevData + this.unaryHistory;
        }
      }
      this.unaryOperations();
    },

    percentage(){
      if(this.evaluated){
        this.prevData = '0';
        this.firstOp = '';
        this.secondOp = '';
        this.operator = '';
        this.unaryHistory = '';
        this.unaryOp = '';
        this.flag = false;
        this.equalFlag  = false;
        this.evaluated = false;
        this.errorExists = false;
        this.operatorLast = false;
      }
      this.unanp
      this.unaryOperations();
    },

    deleteUnaryHistory(){
      this.prevData = this.prevData.slice(0,-(this.unaryHistory.length));
    },

    clearAll() {
      this.currentData = '0';
      this.prevData = '0';
      this.firstOp = '';
      this.secondOp = '';
      this.operator = '';
      this.unaryHistory = '';
      this.unaryOp = '';
      this.flag = false;
      this.equalFlag  = false;
      this.evaluated = false;
      this.errorExists = false;
      this.operatorLast = false;
    },
    del() {
      if(this.evaluated){
        this.firstOp = '';
        this.secondOp = '';
        this.operator = '';
        this.prevData = '0';
      }else{
        if (this.currentData.length === 1) {
          this.currentData = '0';
        } else {
          this.currentData = this.currentData.slice(0, -1);
        }
      }
    },
    sign() {
      if(this.unaryHistory !== ''){
        this.deleteUnaryHistory();
        if(this.unaryHistory.charAt(0) === '-'){
          this.unaryHistory = this.unaryHistory.slice(1);
        }else{
          this.unaryHistory = "-" +this.unaryHistory;
        }
        this.prevData += this.unaryHistory;
      }
      let num;
      num = parseFloat(this.currentData);
      num *= -1;
      this.currentData = num.toString();
      this.negate = true;
    },

     operations(){
      axios.get('http://localhost:8095/calculate',{params: {
        firstOp: this.firstOp,
        secondOp: this.secondOp,
        operator: this.operator,
        }})
          .then(function (response) {
            if(response.data === 'Error'){
              this.currentData = "E";
              this.errorExists = true;
              this.firstOp = '';
              this.secondOp = '';
            }else{
              this.currentData = response.data.toString();
              this.firstOp = this.currentData;
              this.secondOp = '';
            }
          }.bind(this))
    },

    unaryOperations(){
      axios.get('http://localhost:8095/unaryOp',{params: {
          firstOp: this.currentData,
          operator: this.unaryOp,
        }})
          .then(function (response) {
            if(response.data === 'Error'){
              this.currentData = "E";
              this.errorExists = true;
            }else{
              this.currentData = response.data.toString();
            }
          }.bind(this))
    }
  }
}
</script>

<style>
.main {
  background-color: #e0e2e4;
  position: absolute;
  top: 50%;
  left: 50%;
  margin: 0 auto;
  transform: translateX(-50%) translateY(-50%);
}

.Button1 {
  height: 70px;
  width: 70px;
  background-color: #deeaee;
  padding: 1px;
  margin: 2px;
  font-size: 25px;

}

.Button2 {
  height: 70px;
  width: 70px;
  background-color: #92a8d1;
  padding: 1px;
  margin: 2px;
  font-size: 25px;

}

.divview {
  overflow-x: auto;
  height: 100px;
  width: 280px;
  background-color: #e0e2e4;
}

.header {
  color: white;
  text-align: center
}
</style>
