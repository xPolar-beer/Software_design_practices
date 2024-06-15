<template>
  <div>
    <div style="margin: 20px 0">
      <el-select class="input" v-model="timeRange" placeholder="请选择" @change="load">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
    </div>
    <el-card>
      <div id="line" style="width: 100%; height: 400px"></div>
    </el-card>
    <div style="margin: 80px 0;">
      <el-select class="input" v-model="timeRange2" placeholder="请选择" @change="load">
        <el-option
            v-for="item in options2"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-card style="margin-top: 20px">
        <div id="lines" style="width: 100%; height: 400px"></div>
      </el-card>
    </div>
  </div>
</template>

<script>

// import * as echarts from 'echarts'
import Cookies from 'js-cookie'
import * as echarts from 'echarts'
import request from "@/utils/request";

const option = {
  title: {
    text: '房型出售统计'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['单人间', '双人间']
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '单人间',
      type: 'line',
      stack: 'Total',
      smooth: 'true',
      data: []
    },
    {
      name: '双人间',
      type: 'line',
      stack: 'Total',
      smooth: true,
      data: []
    },
  ]
};

const option2 = {
  title: {
    text: '订单金额统计'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['金额']
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '金额',
      type: 'line',
      stack: 'Total',
      smooth: 'true',
      data: []
    },
  ]
};

export default {
  name: 'HomeView',
  data() {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      form: {},
      lineBox: null,  // 定义容器
      timeRange: 'week',
      timeRange2: 'week',
      options: [
        {label: "未来一周", value: 'week'},
        {label: "未来一个月", value: 'month1'},
        {label: "未来两个月", value: 'month2'},
        {label: "未来三个月", value: 'month3'},],
      options2: [
        {label: "过去一周", value: 'week'},
        {label: "过去一个月", value: 'month1'},
        {label: "过去两个月", value: 'month2'},
        {label: "过去三个月", value: 'month3'},]
    }
  },
  mounted() { //等页面元素全部加载后
    this.load();
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      if (!this.lineBox) {
        this.lineBox = echarts.init(document.getElementById('line')) //初始化echarts容器
      }
      request.get('/order/lineCharts/' + this.timeRange).then(res => {
        option.xAxis.data = res.data.date;
        option.series[0].data = res.data.single;
        option.series[1].data = res.data.double;
        this.lineBox.setOption(option)
        // 设置容器的属性  值，当你的数据发送变化后，重新设置
      })
      if (!this.linesBox) {
        this.linesBox = echarts.init(document.getElementById('lines')) //初始化echarts容器
      }
      request.get('/order/lineCharts2/' + this.timeRange2).then(res => {
        option2.xAxis.data = res.data.date;
        option2.series[0].data = res.data.money;
        this.linesBox.setOption(option2)
        // 设置容器的属性  值，当你的数据发送变化后，重新设置
      })
    }
  }
}
</script>

<style scoped>
.input {
  width: 300px;
}
</style>