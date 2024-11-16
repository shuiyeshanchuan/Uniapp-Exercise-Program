<template>
  <div class="home-container">
    <!-- 统计卡片区域 -->
    <div class="stat-cards">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="stat-card notice">
            <div class="icon-wrapper">
              <i class="el-icon-bell"></i>
            </div>
            <div class="stat-info">
              <div class="stat-title">通知总数</div>
              <div class="stat-value">{{ parseInt(stats.noticeCount) || 0 }}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-card users">
            <div class="icon-wrapper">
              <i class="el-icon-user"></i>
            </div>
            <div class="stat-info">
              <div class="stat-title">人员总数</div>
              <div class="stat-value">{{ parseInt(stats.userCount) || 0 }}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-card products">
            <div class="icon-wrapper">
              <i class="el-icon-goods"></i>
            </div>
            <div class="stat-info">
              <div class="stat-title">商品总数</div>
              <div class="stat-value">{{ parseInt(stats.productCount) || 0 }}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-card orders">
            <div class="icon-wrapper">
              <i class="el-icon-shopping-cart-full"></i>
            </div>
            <div class="stat-info">
              <div class="stat-title">订单总数</div>
              <div class="stat-value">{{ parseInt(stats.orderCount) || 0 }}</div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 原有的系统通知部分 -->
    <div class="notice-section">
      <h3>系统通知</h3>
      <el-collapse>
        <el-collapse-item v-for="item in listData" :key="item.id">
          <template slot="title">
            <span style="font-weight: bold">{{ item.name }}</span>
            <i class="header-icon el-icon-info"></i>
          </template>
          <div>发布时间：{{ item.createTime }}</div>
          <div>发布内容：{{ item.content }}</div>
        </el-collapse-item>
      </el-collapse>
    </div>

    <!-- 修改图表部分的布局 -->
    <div class="chart-section">
      <h3>整点花哨小功能</h3>
      <el-row :gutter="20">
        <el-col :span="12">
          <div ref="mainChart" id="main" class="chart-container"></div>
        </el-col>
        <el-col :span="12">
          <div ref="salesChart" id="salesChart" class="chart-container"></div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  data() {
    return {
      listData: [],
      stats: {
        noticeCount: 0,
        userCount: 0,
        productCount: 0,
        orderCount: 0
      },
      chartInstance: null,
      salesChartInstance: null,
      topProducts: []
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initEcharts()
      this.initSalesChart()
      this.loadStats()
      this.loadTopProducts()

      window.addEventListener('resize', this.handleResize)
    })
  },
  created() {
    this.getPageList()
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize)
    if (this.chartInstance) {
      this.chartInstance.dispose()
    }
    if (this.salesChartInstance) {
      this.salesChartInstance.dispose()
    }
  },
  methods: {
    getPageList() {
      this.request.get("/notice/list").then(res => {
        this.listData = res.data
      })
    },

    // 修改 loadStats 方法
    async loadStats() {
      try {
        // 获取通知数量
        const noticeRes = await this.request.get("/notice/count");
        console.log('通知响应数据:', noticeRes); // 添加详细的日志

        // 修改判断条件，同时处理数字和字符串类型的code
        if (noticeRes && (noticeRes.code === 200 || noticeRes.code === '200')) {
          this.stats.noticeCount = parseInt(noticeRes.data) || 0;
          console.log('设置的通知数量:', this.stats.noticeCount); // 添加设置后的值日志
        } else {
          console.log('通知响应异常:', noticeRes);
          this.stats.noticeCount = 0;
        }

        // 获取用户数量
        const userRes = await this.request.get("/sysUser/count");
        if (userRes && (userRes.code === 200 || userRes.code === '200')) {
          this.stats.userCount = parseInt(userRes.data) || 0;
        }

        // 获取商品数量
        const productRes = await this.request.get("/product/count");
        if (productRes && (productRes.code === 200 || productRes.code === '200')) {
          this.stats.productCount = parseInt(productRes.data) || 0;
        }

        // 获取订单数量
        const orderRes = await this.request.get("/userOrder/count");
        if (orderRes && (orderRes.code === 200 || orderRes.code === '200')) {
          this.stats.orderCount = parseInt(orderRes.data) || 0;
        }

        // 打印最终的统计数据
        console.log('最终统计数据:', this.stats);

        // 更新图表
        this.updateChartData();
      } catch (error) {
        console.error('加载统计数据失败:', error);
        this.$message.error('加载统计数据失败');
      }
    },

    handleResize() {
      if (this.chartInstance) {
        this.chartInstance.resize()
      }
      if (this.salesChartInstance) {
        this.salesChartInstance.resize()
      }
    },

    initEcharts() {
      const chartDom = document.getElementById('main')
      if (!chartDom) {
        console.error('找不到主图表 DOM 元素')
        return
      }

      this.chartInstance = echarts.init(chartDom)

      const option = {
        title: {
          text: '系统数据统计',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '数据统计',
            type: 'pie',
            radius: ['30%', '75%'],
            center: ['50%', '60%'],
            roseType: 'radius',
            itemStyle: {
              borderRadius: 8
            },
            label: {
              show: true,
              formatter: '{b}: {c}'
            },
            data: [
              { value: 0, name: '通知' },
              { value: 0, name: '用户' },
              { value: 0, name: '商品' },
              { value: 0, name: '订单' }
            ],
            // 设置不同扇区的颜色
            color: [
              '#FF9A9E',  // 通知
              '#A1C4FD',  // 用户
              '#84FAB0',  // 商品
              '#FAD0C4'   // 订单
            ]
          }
        ]
      }

      this.chartInstance.setOption(option)
    },

    // 更新图表数据的方法
    updateChartData() {
      if (!this.chartInstance) {
        console.error('主图表实例不存在')
        return
      }

      const newData = [
        { value: parseInt(this.stats.noticeCount) || 0, name: '通知' },
        { value: parseInt(this.stats.userCount) || 0, name: '用户' },
        { value: parseInt(this.stats.productCount) || 0, name: '商品' },
        { value: parseInt(this.stats.orderCount) || 0, name: '订单' }
      ]

      console.log('更新主图表数据:', newData)

      this.chartInstance.setOption({
        series: [{
          data: newData
        }]
      })
    },

    // 初始化销量图表
    initSalesChart() {
      const salesChartDom = document.getElementById('salesChart')
      if (!salesChartDom) {
        console.error('找不到销量图表 DOM 元素')
        return
      }

      this.salesChartInstance = echarts.init(salesChartDom)

      const option = {
        title: {
          text: '商品销量统计',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} (件)'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '商品销量',
            type: 'pie',
            radius: ['30%', '75%'],
            center: ['50%', '60%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            label: {
              show: true,
              formatter: '{b}: {c}件'
            },
            data: [],
            color: ['#aec3ce', '#fff6d2', '#cb2749', '#9b8c79']
          }
        ]
      }

      this.salesChartInstance.setOption(option)
    },

    // 加载销量前四的商品数据
    async loadTopProducts() {
      try {
        const res = await this.request.get("/productorder/top-four")
        console.log('获取到的商品销量数据:', res) // 添加日志
        if (res && res.code === 200) {
          this.topProducts = res.data
          console.log('处理后的商品数据:', this.topProducts) // 添加日志
          this.updateSalesChart()
        }
      } catch (error) {
        console.error('加载商品销量数据失败:', error)
        this.$message.error('加载商品销量数据失败')
      }
    },

    // 更新销量图表数据
    updateSalesChart() {
      if (!this.salesChartInstance) {
        console.error('销量图表实例不存在')
        return
      }

      const chartData = this.topProducts.map(item => ({
        value: item.salesCount,
        name: item.pname || '未知商品'
      }))

      console.log('更新销量图表数据:', chartData)

      this.salesChartInstance.setOption({
        series: [{
          data: chartData
        }]
      })
    }
  }
}
</script>

<style scoped>
.home-container {
  padding: 20px;
}

.stat-cards {
  margin-bottom: 30px;
}

.stat-card {
  padding: 20px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  transition: all 0.3s ease;
  cursor: pointer;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.icon-wrapper {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
}

.icon-wrapper i {
  font-size: 30px;
  color: white;
}

.stat-info {
  flex: 1;
}

.stat-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

/* 为每个卡片设置不同的主题色 */
.notice {
  background: linear-gradient(135deg, #ff9a9e 0%, #fad0c4 100%);
}

.users {
  background: linear-gradient(135deg, #a1c4fd 0%, #c2e9fb 100%);
}

.products {
  background: linear-gradient(135deg, #84fab0 0%, #8fd3f4 100%);
}

.orders {
  background: linear-gradient(135deg, #fad0c4 0%, #ffd1ff 100%);
}

.notice .icon-wrapper {
  background: rgba(255, 255, 255, 0.2);
}

.users .icon-wrapper {
  background: rgba(255, 255, 255, 0.2);
}

.products .icon-wrapper {
  background: rgba(255, 255, 255, 0.2);
}

.orders .icon-wrapper {
  background: rgba(255, 255, 255, 0.2);
}

.notice-section {
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.notice-section h3 {
  margin-top: 0;
  margin-bottom: 20px;
  font-size: 18px;
  font-weight: bold;
  color: #303133;
}

/* 适配移动端 */
@media screen and (max-width: 768px) {
  .el-col {
    width: 100%;
    margin-bottom: 15px;
  }
}

/* 修改图表相关样式 */
.chart-section {
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  margin-top: 30px;
  margin-bottom: 30px;
}

.chart-section h3 {
  margin-top: 0;
  margin-bottom: 20px;
  color: #303133;
  font-size: 18px;
  text-align: center;
}

.chart-container {
  width: 100%;
  height: 400px;
  margin: 0 auto;
  position: relative;
}

/* 适配移动端 */
@media screen and (max-width: 768px) {
  .el-col {
    width: 100%;
    margin-bottom: 15px;
  }

  .chart-container {
    height: 300px;
    /* 在移动端稍微降低图表高度 */
  }
}
</style>
