 //控制层 
app.controller('goodsController' ,function($scope,$location,$controller,typeTemplateService,itemCatService,goodsService,uploadService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体   // 获取修改页面传递过来的id   $location   
	//  search（）  location自带的查询方法，
	$scope.findOne=function(){
		var id = $location.search()["id"];
		if(id == null){
			return ;
		}
		goodsService.findOne(id).success(
			function(response){
				console.log(response)
				$scope.entity= response;	
				// 获取富文本编辑器，和图片，让回显
				editor.html($scope.entity.tbGoodsDesc.introduction)
				$scope.entity.tbGoodsDesc.itemImages = 
					JSON.parse($scope.entity.tbGoodsDesc.itemImages)
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	// 查询一级
	$scope.selectItemCatList = function(){
		itemCatService.findItemCatByPid(0).success(
				function(response){
					console.log(response)
					$scope.itemCat1List = response;
				}
		)
	}
	// 查询二级   newValue 就是当前分类的id
	$scope.$watch("entity.tbGooods.category1Id",function(newValue,oldValue){
		// 如果一级分类有值，那么就去获取二级分类
		if(newValue){
			// 查询二级分类
			itemCatService.findItemCatByPid(newValue).success(
					function(response){
						$scope.itemCat2List = response;
//						$scope.entity.tbGooods.category3Id = ""
//						$scope.entity.tbGooods.typeTemplateId = ""
					}
			)
		}
	});
	
	// 查询三级
	$scope.$watch("entity.tbGooods.category2Id",function(newValue,oldValue){
		// 如果一级分类有值，那么就去获取二级分类
		if(newValue){
			// 查询二级分类
			itemCatService.findItemCatByPid(newValue).success(
					function(response){
						$scope.itemCat3List = response;
					}
			)
		}
	});
	
	// 查询模板
	$scope.$watch("entity.tbGooods.category3Id",function(newValue,oldValue){
		if(newValue){
			itemCatService.findOne(newValue).success(
					function(response){
						$scope.entity.tbGooods.typeTemplateId = response.typeId
					}
			)
		}
	});
	
	// 查询品牌
	$scope.$watch("entity.tbGooods.typeTemplateId",function(newValue,oldValue){
		if(newValue){
			typeTemplateService.findOne(newValue).success(
					function(response){
						console.log(response)
						$scope.brandIdList = JSON.parse(response.brandIds);
					}
			)
		}
	})
	
	// 图片上传
	$scope.uploadFile = function(){
		uploadService.uploadFile().success(
				function(response){
					if(response.success){
						$scope.image_entity.imgUrl = response.message;
					}else{
						alert(response.message)
					}
				}
		).error(
			function(){
				alert("上传失败")
			}	
		)
	}
    
	
	// 图片列表
	$scope.entity = {tbGooods:{},tbGoodsDesc:{itemImages:[]}}
	
	$scope.add_image_entity = function(){
		$scope.entity.tbGoodsDesc.itemImages.push($scope.image_entity);
	}
	// 删除图片
	$scope.remove_image_entity = function(index){
		$scope.entity.tbGoodsDesc.itemImages.splice(index,1);
	}
	
	
			
		
	// 添加商品
	$scope.addGood = function(entity){
		var serviceObject;//服务层对象  	
		$scope.entity.tbGoodsDesc.introduction = editor.html();
		entity.tbGoodsDesc.itemImages = JSON.stringify($scope.entity.tbGoodsDesc.itemImages)
		if($scope.entity.tbGooods.id != null){
			serviceObject = goodsService.update(entity);
		}else{
			serviceObject = goodsService.addGood(entity);
		}
		serviceObject.success(
				function(response){
					if(response.success){
						// 清空富文本
						editor.html("");
						// 清空页面数据
						$scope.entity = {}
						window.location.href="../admin/goods.html"
					}else{
						alert(response.message);
					}
				}		
			);			
	};
	
	// 提交审核
	$scope.shenhe = function(){
		goodsService.shenhe($scope.selectIds).success(
				function(response){
					if(response.success){
						$scope.reloadList();// 刷新页面，重新加载
						alert(response.message)
					}else{
						alert(response.message)
					}
				}
		)
		
	}
	
	
	
});	




























