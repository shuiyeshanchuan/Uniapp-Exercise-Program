<template>
  <div>
    <div class="pb10px">
      <el-form ref="searchForm" :model="searchForm" inline>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="searchForm.username" clearable></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="searchForm.email" clearable></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="searchForm.mobile" clearable></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="searchForm.address" clearable></el-input>
        </el-form-item>
        <el-form-item label="角色类型" prop="roleType">
          <el-select v-model="searchForm.roleType" placeholder="请选择" clearable>
            <el-option v-for="item in roleList" :key="item.id" :label="item.name" :value="item.role">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="searchForm.gender" placeholder="请选择" clearable>
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-select v-model="searchForm.state" placeholder="请选择" clearable>
            <el-option label="启用" :value="1" />
            <el-option label="禁用" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="">
          <el-button type="primary" icon="el-icon-search" class="mr10px" @click="handleSearch">搜索</el-button>
          <el-button type="primary" @click="handleAdd" icon="el-icon-plus">新增</el-button>
          <el-button type="danger" size="small" icon="el-icon-remove-outline" @click="batchDelete(null)"
            :disabled="selectionRows.length <= 0">批量删除</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table ref="multipleTable" :data="listData" tooltip-effect="dark" style="width: 100%"
      @selection-change="selectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="50"></el-table-column>
      <el-table-column prop="username" label="用户名称"></el-table-column>
      <el-table-column label="用户头像">
        <template slot-scope="scope">
          <el-image style="width: 50px; height: 50px" :src="scope.row.img"
            :preview-src-list="[scope.row.img]"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="content" label="个人简介" show-overflow-tooltip></el-table-column>
      <el-table-column prop="email" label="邮箱" width="100"></el-table-column>
      <el-table-column prop="mobile" label="电话"></el-table-column>
      <el-table-column prop="address" label="地址" width="100" show-overflow-tooltip></el-table-column>
      <el-table-column prop="gender" label="性别"></el-table-column>
      <el-table-column prop="roleType" label="角色类型"></el-table-column>
      <el-table-column prop="state" label="状态">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.state === '启用'">启用</el-tag>
          <el-tag type="danger" v-if="scope.row.state === '禁用'">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300" fixed="right">
        <template slot-scope="scope">
          <el-button v-if="isAdmin" icon="el-icon-edit-outline"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button v-if="isAdmin" icon="el-icon-remove-outline" type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          <el-button v-if="isAdmin" icon="el-icon-edit-outline" @click="resetPassword(scope.row)">密码重置</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 20px">
      <el-pagination @current-change="currentChange" @size-change="sizeChange" :page-sizes="pageSizes"
        :page-size="pageInfo.pageSize" :current-page="pageInfo.currentPage" background layout="sizes, prev, pager, next"
        :total="pageInfo.total">
      </el-pagination>
    </div>

    <el-drawer :title="formData.id ? '编辑' : '新增'" :visible.sync="isDrawerDialog" size="400px" direction="rtl"
      :wrapper-closable="false" :before-close="closeDrawer">
      <div class="demo-drawer_content" style="padding-right: 40px;box-sizing: border-box">
        <el-form :model="formData" label-width="100px" class="demo-ruleForm">
          <slot name="content">
            <el-form-item label="用户头像" prop="img">
              <el-upload class="avatar-uploader" :action="$httpUploadFile" :show-file-list="false"
                :on-success="uploadImgSuccess">
                <img v-if="formData.img" :src="formData.img" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="用户名称" prop="username">
              <el-input v-model="formData.username" placeholder="用户名称" :disabled="formData.id != null"></el-input>
            </el-form-item>
            <el-form-item label="个人简介" prop="content">
              <el-input type="textarea" rows="5" v-model="formData.content"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="formData.email" placeholder="邮箱"></el-input>
            </el-form-item>
            <el-form-item label="电话" prop="mobile">
              <el-input v-model="formData.mobile" placeholder="电话"></el-input>
            </el-form-item>
            <el-form-item label="地址" prop="address">
              <el-input v-model="formData.address" placeholder="地址"></el-input>
            </el-form-item>
            <el-form-item label="角色类型" prop="roleType">
              <el-select v-model="formData.roleType" placeholder="请选择">
                <el-option v-for="item in roleList" :key="item.id" :label="item.name" :value="item.role">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="性别">
              <el-radio-group v-model="formData.gender">
                <el-radio label="男"></el-radio>
                <el-radio label="女"></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="状态" prop="state">
              <el-radio-group v-model="formData.state">
                <el-radio label="启用"></el-radio>
                <el-radio label="禁用"></el-radio>
              </el-radio-group>
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
import utils from "@/utils/utils";

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
      //删除数据的id
      selectionRows: [],
      formData: {},
      pageSizes: [5, 10, 20, 30],

      ruleForm: {
        username: [
          { required: true, message: '请输入', trigger: 'blur' },
        ],
      },
      roleList: [],
      value: '',
      isAdmin: utils.isAdmin()
    }
  },
  created() {
    this.getPageList()
    this.getRoleList()
  },
  methods: {
    //获取分页数据
    getPageList() {
      let data = Object.assign(this.searchForm, this.pageInfo)
      this.request.get("/sysUser/page", {
        params: data
      }).then(res => {
        this.listData = res.data.list
        this.pageInfo.total = res.data.total
        console.log(res)
      })
    },
    getRoleList() {
      this.request.get("/sysRole/list").then(res => {
        this.roleList = res.data
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
        this.request.post("/sysUser", this.formData).then(res => {
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
        // 路径得写对
        this.request.put("/sysUser/update", this.formData).then(res => {
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
        this.request.delete("/sysUser/", { data: ids }).then(res => {
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


    resetPassword(row) {
      this.request.post("/sysUser/resetPassword/" + row.id).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.isDrawerDialog = false
          this.$message({
            message: res.msg,
            type: 'success'
          });
        }
      })
    },
    uploadImgSuccess(res) {
      this.$set(this.formData, "img", res.data.url)
    }
  }
};
</script>

<style scoped>
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  border: 1px solid #ccc;
  font-size: 28px;
  color: #8c939d;
  width: 150px;
  height: 150px;
  line-height: 150px;
  text-align: center;
}

.avatar {
  width: 150px;
  height: 150px;
  display: block;
}
</style>
