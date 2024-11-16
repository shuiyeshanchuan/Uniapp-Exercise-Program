<template>
  <div>
    <div class="pb10px">
      <el-form ref="searchForm" :model="searchForm" inline>
        <el-form-item label="名称" prop="name">
          <el-input v-model="searchForm.name" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-button type="primary" icon="el-icon-search" class="mr10px" @click="handleSearch">搜索</el-button>
          <el-button type="primary" @click="handleAdd" icon="el-icon-plus">新增</el-button>
          <el-button type="danger" size="small" icon="el-icon-remove-outline" @click="batchDelete(null)"
                     :disabled="selectionRows.length<=0">批量删除
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table ref="multipleTable"
              :data="listData"
              tooltip-effect="dark"
              style="width: 100%"
              @selection-change="selectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="50"></el-table-column>
      <el-table-column prop="name" label="角色名称"></el-table-column>
      <el-table-column prop="role" label="角色字段"></el-table-column>
      <el-table-column prop="content" label="角色描述" show-overflow-tooltip></el-table-column>
      <el-table-column label="操作" width="260" fixed="right">
        <template slot-scope="scope">
          <el-button icon="el-icon-edit-outline" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button icon="el-icon-remove-outline" type="danger" @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 20px">
      <el-pagination
          @current-change="currentChange"
          @size-change="sizeChange"
          :page-sizes="pageSizes"
          :page-size="pageInfo.pageSize"
          :current-page="pageInfo.currentPage"
          background
          layout="sizes, prev, pager, next"
          :total="pageInfo.total">
      </el-pagination>
    </div>
    <el-drawer
        :title="formData.id?'编辑':'新增'"
        :visible.sync="isDrawerDialog"
        size="400px"
        direction="rtl"
        :wrapper-closable="false"
        :before-close="closeDrawer"
    >
      <div class="demo-drawer_content" style="padding-right: 40px;box-sizing: border-box">
        <el-form :model="formData" label-width="100px" class="demo-ruleForm">
          <slot name="content">
            <el-form-item label="角色名称">
              <el-input v-model="formData.name"></el-input>
            </el-form-item>
            <el-form-item label="角色字段">
              <el-input v-model="formData.role"></el-input>
            </el-form-item>
            <el-form-item label="角色描述">
              <el-input v-model="formData.content" type="textarea" rows="10"></el-input>
            </el-form-item>
          </slot>
        </el-form>
      </div>
      <div class="demo-drawer_footer" style="display: flex; justify-content: space-around;">
        <el-button @click="closeDrawer" icon="el-icon-close">取消</el-button>
        <el-button type="primary" @click="submitDrawer" icon="el-icon-check">提交</el-button>
      </div>
    </el-drawer>
  </div>
</template>

<script>

export default {
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
      formData: {},
      pageSizes: [5, 10, 20, 30]
    }
  },

  created() {
    this.getPageList()
  },
  methods: {
    //获取分页数据
    getPageList() {
      let data = Object.assign(this.searchForm, this.pageInfo)
      this.request.get("/sysRole/page", {
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
        this.request.post("/sysRole", this.formData).then(res => {
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
        this.request.put("/sysRole", this.formData).then(res => {
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
      this.isDrawerDialog = true
      console.log(index, row);

      this.formData = Object.assign({}, row)
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
        this.request.delete("/sysRole", {data: ids}).then(res => {
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
  }
};
</script>

<style scoped>
.footer {
  display: flex;
  justify-content: space-around;
}
</style>
