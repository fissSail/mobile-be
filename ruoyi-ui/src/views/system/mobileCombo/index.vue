<template>
  <div class="app-container">

    <el-form :model="filterObj" inline :rules="filterRules" ref="filterRef">
      <el-form-item label="套餐名称" prop="examinePackageName">
        <el-input v-model="filterObj.examinePackageName" />
      </el-form-item>
      <el-form-item label="套餐大写字母" prop="inputCode">
        <el-input v-model="filterObj.inputCode"/>
      </el-form-item>
      <el-form-item label="套餐标题" prop="title">
        <el-input v-model="filterObj.title"/>
      </el-form-item>
      <el-form-item label="标签id" prop="tagId">
        <el-input v-model="filterObj.tagId"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSearch">查 询</el-button>
        <el-button type="primary" @click="handleReset('filterRef')">重 置</el-button>
      </el-form-item>
    </el-form>

    <el-button type="primary" @click="handleToComboDetail">添加套餐</el-button>


    <el-table :data="comboList">
      <el-table-column label="套餐Id" prop="id" align="center"/>
      <el-table-column label="套餐名" prop="name" align="center"/>
      <el-table-column label="套餐标题" prop="title" align="center"/>
      <el-table-column label="套餐内容" prop="content" align="center"/>
      <el-table-column label="销量" prop="saleNumber" align="center"/>
      <el-table-column label="适用年龄" prop="applyAge" align="center"/>
      <el-table-column label="适用性别" prop="applySex" align="center"/>
      <el-table-column label="标签" prop="" align="center">
        <template slot-scope="{row}">
          <el-tag  type="success" v-for="item in row.baseTagVOList">{{item.content}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" prop="updateTime" align="center">
        <template slot-scope="{row}">
          <span>{{GMETtIME(row.updateTime)}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作"  width="300px">
        <template slot-scope="{row}"  >
          <el-button size="mini" type="primary" @click="handleToComboDetail(row)">更新套餐</el-button>
          <el-button size="mini" type="primary" @click="handleRemoveCombo(row.id)">删除套餐</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      layout="prev, pager, next"
      @current-change="handleSizeChange"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>

import {setMealList,deleteCombo,updateCombo,getMobileList} from '@/api/combo'
import comboDetail from './comboDetail'
  export default {
    name:'mobieCombo',
    data(){
      return {
        //过滤参数
        filterObj:{
          baseExaminePackageId:'',
          title:'',
          inputCode:'',
          tagId:'',
          pageNum:1,
          pageSize:10,
        },


        total:0,
        comboList:[],//套餐数据
        tagValue:'',
        dynamicTags:[],
        inputVisible:false,
        filterRules:{}
      }
    },
    created(){
      this.getComboList()

    },
    computed:{
      GMETtIME(){
        return function(time){

          let date = new Date(time)
          let Str=date.getFullYear() + '-' +
            (date.getMonth() + 1) + '-' +
            date.getDate() + ' ' +
            date.getHours() + ':' +
            date.getMinutes() + ':' +
            date.getSeconds()
          return Str

        }
      },
    },
    methods:{
      //获取到套餐列表数据
      getComboList(){
        getMobileList(this.filterObj).then(res => {
          this.comboList = res.data.records
          this.total = res.data.total
        })
      },

      //跳转到修改套餐信息界面
      handleToComboDetail(row){
        this.$router.push({
          path: '/system/comboDetail',
          query:{
            comboDetail:row
          }
        })
      },

      //查询套餐列表
      handleSearch(){
        this.filterObj.pageNum =1
        this.getComboList()
      },

      //重置
      handleReset(formName){
        this.$refs[formName].resetFields();
        this.getComboList()
      },


      //删除套餐信息
      handleRemoveCombo(id){
        this.$confirm('是否将此套餐取消关联到公众号, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteCombo([id]).then(res => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.getComboList()
          }).catch(err=>{
            this.$message.success('删除失败')
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });

      },
      handleSizeChange(curretn){
        this.filterObj.pageNum = curretn
          this.getComboList()
      },

    }
  }
</script>

<style scoped>
  >>> .el-tag{
    margin-left:5px;
    margin-top:5px;
  }
</style>
