 //商品类目控制层 
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	
	// 保存数据的时候，要获取上级的id
	$scope.parentId = 0;
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
			// 在添加时，获取它的父id
			$scope.entity.parentId = $scope.parentId;
			serviceObject=itemCatService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
					$scope.findItemCatByPid($scope.parentId);
//		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		itemCatService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.findItemCatByPid($scope.parentId);
//					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	// 根据parentid查询商品分类
	$scope.findItemCatByPid = function(parentId){
		$scope.parentId = parentId;
		itemCatService.findItemCatByPid(parentId).success(
				function(response){
					$scope.list = response;
				}
		)
	}
	
	// 如果查询到第三级的时候，要屏蔽下一级查询按钮
	$scope.jibie = 1;  // 默认一级
	
	// 修改级别的结果,默认是1
	$scope.setJieBie = function(value){
		$scope.jibie = value;
	}
	// 切换级别，隐藏
	$scope.selectJiebie = function(entity){
		if($scope.jibie == 1){  // 如果是1级
			$scope.entity_1 = null;
			$scope.entity_2 = null;
			$scope.entity_3 = null;
		}
		if($scope.jibie == 2){
			$scope.entity_2 = entity;
			$scope.entity_3 = null;
		}
		if($scope.jibie == 3){
			$scope.entity_3 = entity;
		}
		
		$scope.findItemCatByPid(entity.id);
	}
	
	// 查询select2
	$scope.typeTemplateList = {data:[]}
	$scope.selectOptionList = function(){
		itemCatService.selectOptionList().success(
				function(response){
					$scope.typeTemplateList = {data:response}
				}
		)
	}
	
    
});	


























