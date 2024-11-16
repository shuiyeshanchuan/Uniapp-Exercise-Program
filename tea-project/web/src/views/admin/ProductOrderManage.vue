<template>
  <div>
    <div class="pb10px">
      <el-form ref="searchForm" :model="searchForm" inline>
        <el-form-item label="订单号">
          <el-input v-model="searchForm.oid" placeholder="请输入订单号"></el-input>
        </el-form-item>
        <el-form-item label="产品ID">
          <el-input v-model="searchForm.pid" placeholder="请输入产品ID"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch" icon="el-icon-search">搜索</el-button>
          <el-button @click="handleReset" icon="el-icon-refresh">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table :data="listData" tooltip-effect="dark" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="oid" label="订单号"></el-table-column>
      <el-table-column prop="pid" label="产品ID" width="100"></el-table-column>
      <el-table-column prop="pname" label="产品名称" width="150"></el-table-column>
      <el-table-column label="产品图片" width="100">
        <template slot-scope="scope">
          <el-image v-if="scope.row.pimageurl" style="width: 50px; height: 50px" :src="scope.row.pimageurl" fit="cover"
            :preview-src-list="[scope.row.pimageurl]">
          </el-image>
          <span v-else>暂无图片</span>
        </template>
      </el-table-column>
      <el-table-column prop="number" label="数量"></el-table-column>
      <el-table-column prop="price" label="价格">
        <template slot-scope="scope">
          ￥{{ scope.row.price }}
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 20px">
      <el-pagination @current-change="currentChange" @size-change="sizeChange" :page-sizes="pageSizes"
        :page-size="pageInfo.pageSize" :current-page="pageInfo.pageNum" background layout="sizes, prev, pager, next"
        :total="pageInfo.total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProductorderManage',
  data() {
    return {
      listData: [],
      pageInfo: {
        pageNum: 1,
        pageSize: 10,
        total: 0
      },
      searchForm: {
        oid: '',
        pid: ''
      },
      pageSizes: [5, 10, 20, 30]
    }
  },
  created() {
    // 如果URL中有订单号参数，则自动填充搜索条件
    const oid = this.$route.query.oid
    if (oid) {
      this.searchForm.oid = oid
      this.handleSearch()
    } else {
      this.getPageList()
    }
  },
  methods: {
    getPageList() {
      // 只有当搜索条件有值时才添加到请求参数中
      let params = {
        pageNum: this.pageInfo.pageNum,
        pageSize: this.pageInfo.pageSize
      }

      // 只添加有值的搜索条件
      if (this.searchForm.oid) {
        params.oid = this.searchForm.oid.trim()
      }
      if (this.searchForm.pid) {
        params.pid = this.searchForm.pid.trim()
      }

      this.request.get("/productorder/page", {
        params: params
      }).then(res => {
        console.log('搜索参数:', params)  // 调试日志
        if (res.code === 200) {
          // 如果后端不支持搜索，我们在前端进行过滤
          let filteredData = res.data.list
          if (this.searchForm.oid) {
            filteredData = filteredData.filter(item =>
              item.oid === this.searchForm.oid.trim()
            )
          }
          if (this.searchForm.pid) {
            filteredData = filteredData.filter(item =>
              item.pid === this.searchForm.pid.trim()
            )
          }

          this.listData = filteredData
          this.pageInfo.total = filteredData.length
        } else {
          this.$message.error(res.msg || '获取数据失败')
        }
      }).catch(err => {
        console.error('请求错误:', err)
        this.$message.error('获取数据失败')
      })
    },

    handleSearch() {
      // 重置页码到第一页
      this.pageInfo.pageNum = 1
      // 验证搜索条件是否为空
      if (!this.searchForm.oid && !this.searchForm.pid) {
        this.$message.warning('请至少输入一个搜索条件')
        return
      }
      this.getPageList()
    },
    currentChange(val) {
      this.pageInfo.pageNum = val
      this.getPageList()
    },
    sizeChange(val) {
      this.pageInfo.pageSize = val
      this.getPageList()
    },
    handleReset() {
      // 重置表单
      this.$refs.searchForm.resetFields()
      // 重置搜索条件
      this.searchForm = {
        oid: '',
        pid: ''
      }
      // 重置分页
      this.pageInfo.pageNum = 1
      this.pageInfo.pageSize = 10
      // 重新加载数据
      this.getPageList()
    }
  }
}
</script>

<style scoped>
.pb10px {
  padding-bottom: 10px;
}

.el-image {
  border-radius: 4px;
}
</style>
