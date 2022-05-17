<template>
    <div class="app-container wrapper">
      <el-form :model="connectToMobile" ref="formRef" label-width="120px" :rules="formRules">
        <el-form-item label="套餐" prop="baseExaminePackageId">
          <el-select  v-model="connectToMobile.baseExaminePackageId"
                      filterable placeholder="请输入标签内容"
                      :disabled="($route.query.comboDetail.hasOwnProperty('id') && $route.query.comboDetail.id !== '')">
            <el-option
              v-for="(item,index) in pcComboList"
              :key="index"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
            <el-form-item label="体检套餐标题" prop="title">
              <el-input v-model="connectToMobile.title"  style="width: 300px"></el-input>
            </el-form-item>
            <el-form-item label="套餐详情" prop="content">
              <el-input v-model="connectToMobile.content" type="textarea" style="width: 300px"></el-input>
            </el-form-item>

            <el-form-item label="套餐标签" prop="oldtabValue">
              <el-select multiple v-model="connectToMobile.oldtabValue" filterable placeholder="请输入标签内容">
                <el-option
                  v-for="(item,index) in tabList"
                  :key="index"
                  :label="item.content"
                  :value="item.id">
                </el-option>
              </el-select>
              <el-button type="primary" @click="handleNewTag" style="margin-left:10px;">添加套餐标签</el-button>
              <div>
                <el-tag v-for="(tag,index) in dynamicTags" :key="index"  @click="handleTagChange(tag)" style="margin-left:10px;">
                  {{tag.content}} /  {{formmateSex(sexList,tag.applySex)}} / {{tag.minApplyAge}}-{{tag.maxApplyAge}}
                </el-tag>
              </div>
            </el-form-item >

            <el-form-item label="缩略图">
              <Upload  @imgList="handleSmall"></Upload>
              <el-button style="margin-top:10px" type="primary" @click="handleConfirmSmall">设为缩略图</el-button>
            </el-form-item>
            <el-form-item label="轮播图">
              <Upload  @imgList="travelCardImgList"></Upload>
              <el-button style="margin-top:10px" type="primary" @click="handleConfirmSwiper">设为轮播图</el-button>
            </el-form-item>
            <el-form-item label="销量" prop="saleNumber">
              <el-input v-model="connectToMobile.saleNumber" type="number" style="width: 300px"></el-input>
            </el-form-item>
        <el-form-item>
          <el-button @click="$route.goback()">取消</el-button>
          <el-button type="primary" @click="handleConfirmSave">关联到移动端</el-button>
        </el-form-item>
      </el-form>
      <el-dialog
        title="添加标签"
        :visible.sync="dialogVisible"
        width="30%"
        >
       <div>
         <el-form :model="tabForm" label-width="120px">
           <el-form-item label="适用性别" prop="applySex">
             <el-select v-model="tabForm.applySex" placeholder="请输入适用性别" style="width: 300px">
               <el-option
                 v-for="(item,index) in sexList"
                 :key="index"
                 :label="item.dictLabel"
                 :value="item.dictValue">
               </el-option>
             </el-select>
           </el-form-item>
           <el-form-item label="标签内容">
             <el-input v-model="tabForm.content" placeholder="请输入标签内容" style="width: 300px"></el-input>
           </el-form-item>
           <el-form-item label="适用年龄"  prop="applyAge">
             <el-input type="number" :min="1"  v-model="tabForm.minApplyAge" placeholder="最小适用年龄" style="width: 150px"></el-input>
             至<el-input type="number" :min="1"  v-model="tabForm.maxApplyAge" placeholder="最大适用年龄" style="width: 150px"></el-input>
           </el-form-item>
         </el-form>
       </div>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="handleInputConfirm">确 定</el-button>
  </span>
      </el-dialog>
    </div>
</template>

<script>

  import Upload from '@/components/Upload/Upload'
  import {upload,uploads,getTagList,updateAndAddTag,updateCombo,setMealList} from '@/api/combo.js'
  export default {
    name: 'comboDetail',
    components:{Upload},
    data(){
      return {
        connectToMobile:{
          baseExaminePackageId:'',
          title:'',
          content:'',
          image:"",//缩略图
          banner:'',//轮播图
          tagIdList:'',
          saleNumber:0,
          oldtabValue:[],
        },
        tabForm:{
          applySex:'',
          content:'',
          minApplyAge:'',
          maxApplyAge:'',
          id:''
        },
        sexList:[{dictLabel:'男',dictValue:'1'},{dictLabel:'女',dictValue:'2'},{dictLabel:'通用',dictValue:'3'}],
        tabList:[],
        pcComboList:[],
        dynamicTags:[],
        imgList:[],
        swiperImg:[],
        tabId:'',//修改标签的id
        selectTag:[], //选择的tabid
        formRules:{
          baseExaminePackageId:[{ required: true, message: '请输入体检套餐id', trigger: 'blur' }],
          name:[{ required: true, message: '请输入套餐标题', trigger: 'blur' }],
          title:[{ required: true, message: '请输入套餐标题', trigger: 'blur' }],
          content:[{ required: true, message: '请输入套餐内容', trigger: 'blur' }],
          oldtabValue:[{ required: true, message: '请选择套餐标签', trigger: 'change' }],
          saleNumber:[{ required: true, message: '请输入套餐销量', trigger: 'blur' }],
         },
        dialogVisible:false,
        newtag:false,
      }
    },
    created(){
      //获取套餐信息
      if(this.$route.query.comboDetail){
        console.log("$route.query.comboDetail",this.$route.query.comboDetail)
        const comboDetail = this.$route.query.comboDetail
        for(let i in this.connectToMobile){
          this.connectToMobile[i] = comboDetail[i]
        }
      }
      this.handleShowTagList()
      this.handlePcComboList()
    },

    methods:{
      async handlePcComboList(){
        setMealList({name:this.connectToMobile.baseExaminePackageId}).then(res => {
          this.pcComboList = res.data.records
        })
      },

      //获取标签列表
      async handleShowTagList() {
        let params ={
          content:''
        }
        const res = await getTagList(params) // 加载酒店下拉列表
        this.tabList = res.data.records
      },

      //修改标签
      handleTagChange(tag){
        this.newtag= false
        for(let i  in this.tabForm){
          this.tabForm[i] = tag[i]
        }
        this.tabForm.applySex = String(tag.applySex)
        this.dialogVisible = true
      },

      formmateSex(sexOptions,sexType){
        return sexOptions.find(item => {
          return item.dictValue == sexType
        }).dictLabel
      },
      //添加新标签
      handleNewTag(){
        this.newtag= true
        for(let i  in this.tabForm){
          this.tabForm[i] = ''
        }
        this.dialogVisible = true
      },

      //添加标签
      handleInputConfirm() {
         updateAndAddTag(this.tabForm).then(res => {
           if(this.newtag){
             this.dynamicTags.push(res.data.data)
           }else{
             this.dynamicTags.forEach((item,index) => {
               if(item.id === res.data.data.id){
                 this.dynamicTags[index] = res.data.data
               }
             })
           }
            this.dialogVisible = false
          })
      },

      handleSmall(file){
        this.imgList = file
      },
      travelCardImgList(file){
       this.swiperImg = file
      },

      dataURLtoFile(dataurl, filename) {
        var arr = dataurl.split(",");
        var mime = arr[0].match(/:(.*?);/)[1];
        var bstr = atob(arr[1]);
        var n = bstr.length;
        var u8arr = new Uint8Array(n);
        while (n--) {
          u8arr[n] = bstr.charCodeAt(n);
        }
        return new File([u8arr], filename, { type: mime });
      },


      //上传缩略图
      handleConfirmSmall(){
        const img = this.imgList.toString()
        this.imgFile = this.dataURLtoFile(
          img,
          new Date().getTime() + ".png"
        );
        let data = new FormData();
        data.append("file", this.imgFile);
        upload(data).then(res => {
          if (res.code == 200) {
            this.$message({
              message: "上传成功",
              type: "success",
            });
            this.connectToMobile.image = res.fileName.toString()
          }
        })
      },

      //上传轮播图
      handleConfirmSwiper(){
        const img = this.swiperImg.toString()
        this.imgFile = this.dataURLtoFile(
          img,
          new Date().getTime() + ".png"
        );

        let data = new FormData();
        data.append("files", this.imgFile);
        uploads(data).then(res => {
          if (res.code == 200) {
            this.$message({
              message: "上传成功",
              type: "success",
            });
            this.connectToMobile.banner = res.fileNames.toString()
          }
        })
      },
      //保存或更新关联套餐信息
      handleConfirmSave(){
        this.$refs.formRef.validate((valid) => {
          if(!valid) return
          this.connectToMobile.tagIdList = [...this.connectToMobile.oldtabValue, ...this.dynamicTags.map(item => item.id)]
          delete this.connectToMobile.oldtabValue
          console.log("this.connectToMobile",this.connectToMobile)
            updateCombo(this.connectToMobile).then(res => {
              if(res.code == 200){
                this.$message.success("关联成功")
                this.$router.go(-1)
              }else{
                this.$message.success("关联失败")
              }
            })

        })

      },

    },
  }
</script>

<style scoped>
.wrapper{
  background-color: white;

  margin:0 20px;
  box-shadow: -5px 5px 2px #eee;
}
  .button-new-tag{
    margin-left:5px;
  }
</style>
