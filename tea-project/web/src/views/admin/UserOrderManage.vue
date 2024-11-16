<template>
  <div class="user-order-manage">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-form ref="searchForm" :model="searchForm" inline>
        <el-form-item>
          <!-- <el-button type="primary" @click="handleAdd" icon="el-icon-plus" size="medium">新增订单</el-button> -->
          <el-button type="danger" size="medium" icon="el-icon-delete" @click="batchDelete(null)"
            :disabled="selectionRows.length <= 0">
            批量删除
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 订单列表 -->
    <el-card class="order-table-card" shadow="hover">
      <el-table ref="multipleTable" :data="listData" tooltip-effect="dark" style="width: 100%"
        @selection-change="selectionChange" border stripe :header-cell-style="{ background: '#f5f7fa' }">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
        <el-table-column prop="otype" label="订单类型" width="100" align="center">
          <template slot-scope="scope">
            <el-tag :type="scope.row.otype === 1 ? 'success' : 'warning'" size="small">
              {{ scope.row.otype === 1 ? '外卖' : '自取' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="onumber" label="订单流水号" min-width="180"></el-table-column>
        <el-table-column prop="uid" label="用户ID" width="100" align="center"></el-table-column>
        <el-table-column label="订单详情" width="100" align="center">
          <template slot-scope="scope">
            <el-button type="text" @click="showOrderDetail(scope.row)" icon="el-icon-document">
              查看详情
            </el-button>
          </template>
        </el-table-column>
        <el-table-column prop="oprice" label="订单价格" width="120" align="right">
          <template slot-scope="scope">
            <span class="price-text">￥{{ scope.row.oprice }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="paystate" label="支付状态" width="100" align="center">
          <template slot-scope="scope">
            <el-tag :type="scope.row.paystate === 1 ? 'success' : 'danger'" size="small">
              {{ scope.row.paystate === 1 ? '已支付' : '未支付' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="otime" label="支付时间" width="160" align="center"></el-table-column>
        <el-table-column prop="ostate" label="订单状态" width="120" align="center">
          <template slot-scope="scope">
            <el-tag :type="getOrderStateType(scope.row.ostate)" size="small">
              {{ getOrderStateText(scope.row.ostate) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" fixed="right" align="center">
          <template slot-scope="scope">
            <div class="operation-buttons">
              <el-button type="text" size="mini" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">
                编辑
              </el-button>
              <el-button type="text" size="mini" icon="el-icon-delete" class="delete-btn"
                @click="handleDelete(scope.$index, scope.row)">
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination @current-change="currentChange" @size-change="sizeChange" :page-sizes="pageSizes"
          :page-size="pageInfo.pageSize" :current-page="pageInfo.currentPage" background
          layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.total">
        </el-pagination>
      </div>
    </el-card>

    <!-- 订单详情弹窗 -->
    <el-dialog title="订单详情" :visible.sync="detailDialogVisible" width="60%" :close-on-click-modal="false"
      custom-class="order-detail-dialog">
      <div v-if="currentOrderDetail" class="order-detail-container">
        <el-card class="detail-card">
          <!-- 订单基本信息 -->
          <div class="info-section">
            <el-descriptions :column="2" size="small" border>
              <el-descriptions-item label="订单编号">
                <el-tag size="small">{{ currentOrderDetail.onumber }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="订单类型">
                <el-tag size="small" :type="currentOrderDetail.otype === 1 ? 'success' : 'warning'">
                  {{ currentOrderDetail.otype === 1 ? '外卖' : '自取' }}
                </el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="支付状态">
                <el-tag size="small" :type="currentOrderDetail.paystate === 1 ? 'success' : 'danger'">
                  {{ currentOrderDetail.paystate === 1 ? '已支付' : '未支付' }}
                </el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="订单状态">
                <el-tag size="small" :type="getOrderStateType(currentOrderDetail.ostate)">
                  {{ getOrderStateText(currentOrderDetail.ostate) }}
                </el-tag>
              </el-descriptions-item>
            </el-descriptions>
          </div>

          <!-- 商品列表 -->
          <div class="goods-section">
            <div class="section-header">
              <span>商品信息</span>
              <span class="total-count">共 {{ productOrders.length }} 件商品 | 总计：￥{{ currentOrderDetail.oprice }}</span>
            </div>
            <el-table :data="productOrders" style="width: 100%" size="small" border>
              <el-table-column prop="pid" label="商品ID" width="100" align="center"></el-table-column>
              <el-table-column prop="pname" label="商品名称" min-width="120"></el-table-column>
              <el-table-column label="商品图片" width="100" align="center">
                <template slot-scope="scope">
                  <el-image v-if="scope.row.pimageurl" class="product-image" :src="scope.row.pimageurl" fit="cover"
                    :preview-src-list="[scope.row.pimageurl]">
                  </el-image>
                  <span v-else>暂无图片</span>
                </template>
              </el-table-column>
              <el-table-column prop="number" label="数量" width="80" align="center">
                <template slot-scope="scope">
                  <span>× {{ scope.row.number }}</span>
                </template>
              </el-table-column>
              <el-table-column label="金额" width="100" align="right">
                <template slot-scope="scope">
                  <span class="subtotal">￥{{ (scope.row.price * scope.row.number).toFixed(2) }}</span>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="detailDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>

    <!-- 编辑弹窗 -->
    <el-dialog :title="formData.id ? '编辑订单' : '新增订单'" :visible.sync="dialogVisible" width="50%"
      :close-on-click-modal="false">
      <el-form :model="formData" :rules="rules" ref="formRef" label-width="100px" size="small">
        <el-form-item label="订单类型" prop="otype">
          <el-radio-group v-model="formData.otype">
            <el-radio :label="1">外卖</el-radio>
            <el-radio :label="2">自取</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="订单流水号" prop="onumber">
          <el-input v-model="formData.onumber" placeholder="请输入订单流水号"></el-input>
        </el-form-item>
        <el-form-item label="用户ID" prop="uid">
          <el-input v-model="formData.uid" placeholder="请输入用户ID"></el-input>
        </el-form-item>
        <el-form-item label="订单价格" prop="oprice">
          <el-input-number v-model="formData.oprice" :precision="2" :step="0.1" :min="0" controls-position="right"
            placeholder="请输入订单价格">
          </el-input-number>
        </el-form-item>
        <el-form-item label="支付状态" prop="paystate">
          <el-radio-group v-model="formData.paystate">
            <el-radio :label="1">已支付</el-radio>
            <el-radio :label="0">未支付</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="订单状态" prop="ostate">
          <el-select v-model="formData.ostate" placeholder="请选择订单状态">
            <el-option label="预定中" :value="0"></el-option>
            <el-option label="已收货" :value="1"></el-option>
            <el-option label="退订" :value="2"></el-option>
            <el-option label="禁用" :value="3"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitDrawer">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>


export default {
  components: {},
  data() {
    return {
      //抽屉控制
      isDrawerDialog: false,
      //列表数据
      listData: [],
      //分页详情
      pageInfo: {
        //当前页
        pageNum: 1,
        //分页大小
        pageSize: 10,
        //总条数
        total: 0
      },
      //搜索
      searchForm: {
        name: ""
      },

      selectionRows: [],
      formData: {
        id: null,
        otype: 1,
        onumber: '',
        uid: '',
        oprice: 0,
        paystate: 0,
        ostate: 0
      },
      pageSizes: [5, 10, 20, 30],
      dialogVisible: false,
      orderDetail: null,
      detailDialogVisible: false,
      currentOrderDetail: null,
      productOrders: [],
      rules: {
        otype: [{ required: true, message: '请选择订单类型', trigger: 'change' }],
        onumber: [{ required: true, message: '请输入订单流水号', trigger: 'blur' }],
        uid: [{ required: true, message: '请输入用户ID', trigger: 'blur' }],
        oprice: [{ required: true, message: '请输入订单价格', trigger: 'blur' }],
        paystate: [{ required: true, message: '请选择支付状态', trigger: 'change' }],
        ostate: [{ required: true, message: '请选择订单状态', trigger: 'change' }]
      }
    }
  },

  created() {
    this.getPageList()
  },
  methods: {

    // // 打开编辑弹窗
    // handleEdit(index, row) {
    //   this.dialogVisible = true
    //   this.formData = { ...row }  // 复制一份数据，避免直接修改
    // },

    // // 打开新增弹窗
    // handleAdd() {
    //   this.dialogVisible = true
    //   this.formData = {
    //     otype: 1,
    //     onumber: '',
    //     uid: '',
    //     oprice: 0,
    //     paystate: 0,
    //     ostate: 0
    //   }
    // },

    // // 保存或更新
    // saveOrUpdate() {
    //   this.$refs.formRef.validate((valid) => {
    //     if (valid) {
    //       const url = this.formData.id ? '/userOrder/update' : '/userOrder/add'
    //       const method = this.formData.id ? 'put' : 'post'

    //       this.request[method](url, this.formData).then(res => {
    //         if (res.code === 200) {
    //           this.$message.success('保存成功')
    //           this.dialogVisible = false
    //           this.getPageList()
    //         } else {
    //           this.$message.error(res.msg || '保存失败')
    //         }
    //       })
    //     }
    //   })
    // },

    //获取分页数据
    getPageList() {
      let data = Object.assign(this.searchForm, this.pageInfo)
      this.request.get("/userOrder/page", {
        params: data
      }).then(res => {
        this.listData = res.data.list
        this.pageInfo.total = res.data.total
        console.log(res)
      })
    },
    //选择分页
    currentChange(e) {
      this.pageInfo.pageNum = e
      this.getPageList()
      console.log(e)
    },
    //改变分页数量
    sizeChange(e) {
      this.pageInfo.pageSize = e
      this.getPageList()
      console.log(e)
    },
    //搜索
    handleSearch() {
      this.pageInfo.pageNum = 1
      this.getPageList()
    },
    //关闭抽屉
    closeDrawer() {
      this.isDrawerDialog = false
    },
    //提交数据
    submitDrawer() {
      //新增
      if (!this.formData.id) {
        this.request.post("/userOrder", this.formData).then(res => {
          console.log(res)
          if (res.code === 200) {
            this.isDrawerDialog = false
            this.$message({
              message: res.msg,
              type: 'success'
            });
            this.getPageList()
          }
        })
      } else {
        //更新
        this.request.put("/userOrder", this.formData).then(res => {
          console.log(res)
          if (res.code === 200) {
            this.isDrawerDialog = false
            this.$message({
              message: res.msg,
              type: 'success'
            });
            this.getPageList()
          }
        })
      }
    },
    //添加数据
    handleAdd() {
      this.isDrawerDialog = true
      this.formData = {}
    },
    //编辑
    handleEdit(index, row) {
      this.dialogVisible = true
      this.formData = { ...row }  // 复制一份数据，避免直接修改
    },
    selectionChange(rows) {
      this.selectionRows = rows
    },
    //单个删除
    handleDelete(index, row) {
      this.batchDelete([row])
    },
    //批量删除
    batchDelete(rows) {
      if (!rows) {
        rows = this.selectionRows;
      }
      let ids = rows.map(item => item.id);
      this.$confirm(`此操作将永久删除ID为[${ids}]的数据, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        //批量删除数据
        this.request.delete("/userOrder/", { data: ids }).then(res => {
          if (res.code === 200) {
            this.$message({
              message: res.msg,
              type: 'success'
            });
            this.getPageList()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
        this.$refs.multipleTable.clearSelection();
      });
    },
    // 获取订单详情
    getOrderDetail() {
      const ostate = this.formData.ostate
      console.log('请求参数 ostate:', ostate)

      this.request.get("/userOrder/queryOrder", {
        params: {
          ostate: ostate
        }
      }).then(res => {
        console.log('接口返回的完整响应：', res)
        console.log('订单详情数据：', res.data)

        if (res.code === 200) {
          this.orderDetail = res.data
          this.dialogVisible = true
          console.log('存储到 orderDetail 的数据：', this.orderDetail)
        } else {
          this.$message.error('获取订单详情失败')
          console.error('请求失败：', res.msg)
        }
      }).catch(error => {
        console.error('请求发生错误：', error)
      })
    },

    // 订单状态文字转换
    getOrderStateText(state) {
      const stateMap = {
        '0': '预定中（未收货）',
        '1': '已收货',
        '2': '退订',
        '3': '禁用'
      }
      return stateMap[state] || '未知状态'
    },

    // 显示订单详情
    showOrderDetail(row) {
      this.currentOrderDetail = row;
      this.detailDialogVisible = true;
      // 获取该订单的产品订单详情
      this.getProductOrders(row.onumber);
    },

    // 获取产品订单详情
    getProductOrders(onumber) {
      this.request.get("/productorder/page", {
        params: {
          oid: onumber,
          pageNum: 1,
          pageSize: 100  // 设置较大的数值以获取所有数据
        }
      }).then(res => {
        if (res.code === 200) {
          this.productOrders = res.data.list || [];
        } else {
          this.$message.error('获取订单商品详情失败');
        }
      }).catch(err => {
        console.error('获取订单商品详情失败:', err);
        this.$message.error('获取订单商品详情失败');
      });
    },

    // 获取订单状态对应的Tag类型
    getOrderStateType(state) {
      const typeMap = {
        '0': 'warning',   // 预定中
        '1': 'success',   // 已收货
        '2': 'danger',    // 退订
        '3': 'danger'     // 禁用
      }
      return typeMap[state] || 'unknown'
    },

    // 保存或更新
    saveOrUpdate() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          const url = this.formData.id ? '/userOrder/update' : '/userOrder/add'
          const method = this.formData.id ? 'put' : 'post'

          this.request[method](url, this.formData).then(res => {
            if (res.code === 200) {
              this.$message.success('保存成功')
              this.dialogVisible = false
              this.getPageList()
            } else {
              this.$message.error(res.msg || '保存失败')
            }
          })
        }
      })
    }
  }
};
</script>

<style scoped>
.user-order-manage {
  padding: 20px;
  background: #f0f2f5;
  min-height: calc(100vh - 84px);
}

.operation-bar {
  margin-bottom: 20px;
  padding: 15px 20px;
  background: white;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 21, 41, .08);
}

.order-table-card {
  margin-bottom: 20px;
}

.pagination-container {
  padding: 20px 0;
  text-align: right;
}

/* 表格内容样式 */
.el-table {
  margin: 10px 0;
}

.price-text {
  color: #f56c6c;
  font-weight: 500;
}

.delete-btn {
  color: #f56c6c;
}

.delete-btn:hover {
  color: #ff4949;
}

/* 标签样式 */
.el-tag {
  border-radius: 12px;
  padding: 0 10px;
}

/* 按钮样式 */
.el-button [class*="el-icon-"]+span {
  margin-left: 5px;
}

.el-button--text {
  padding: 0 8px;
}

/* 抽屉和弹窗样式保持不变 */

/* 响应式处理 */
@media screen and (max-width: 1200px) {
  .el-table {
    width: 100%;
    overflow-x: auto;
  }
}

/* 订单详情弹窗样式 */
.order-detail-dialog {
  border-radius: 4px;
}

.order-detail-container {
  padding: 0 10px;
}

.detail-card {
  margin-bottom: 10px;
}

.info-section {
  margin-bottom: 20px;
}

.goods-section {
  margin-top: 20px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid #ebeef5;
}

.total-count {
  font-size: 13px;
  color: #909399;
}

.product-image {
  width: 40px;
  height: 40px;
  border-radius: 4px;
}

.subtotal {
  color: #f56c6c;
  font-size: 13px;
}

/* 调整表格样式 */
.el-table {
  font-size: 13px;
}

.el-table td {
  padding: 8px 0;
}

.el-descriptions-item__label {
  width: 100px;
}

/* 操作按钮样式 */
.operation-buttons {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
  /* 按钮之间的间距 */
}

.operation-buttons .el-button {
  padding: 0 4px;
  margin: 0;
  font-size: 12px;
}

.operation-buttons .el-button+.el-button {
  margin-left: 0;
  /* 覆盖 element-ui 的默认间距 */
}

.delete-btn {
  color: #f56c6c;
}

.delete-btn:hover {
  color: #ff4949;
}

/* 编辑弹窗样式 */
.el-dialog__body {
  padding: 20px 40px;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-input-number {
  width: 100%;
}

.el-select {
  width: 100%;
}

/* 调整radio组样式 */
.el-radio {
  margin-right: 20px;
  line-height: 32px;
}

.el-radio:last-child {
  margin-right: 0;
}

/* 调整按钮样式 */
.dialog-footer {
  text-align: right;
}

.dialog-footer .el-button+.el-button {
  margin-left: 10px;
}
</style>
