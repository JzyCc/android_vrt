var COMMON_YELLOW = createColorWithRGBA(255,153,18,255);
var COMMON_DARK = createColorWithRGBA(50,51,52,255);
const _this = this;

var staticSourceBaseUrl = "http://21xa689434.imwork.net:8090/";

//获取导航控制器、且获取参数
var param = getThisNavigation().getPushedParam();
//创建唯一的视图控制器
var mainVC = new ViewController(false,true);


function displayLabelS1()
{
    Label.call(this);
    this.cornerRadius = 5 * kHeightScale;
    this.textAlignment = TextAlignmentCenter;
    this.textColor = whiteColor;
    this.fontSize = 12 * kFontScale;
}

var mainView = mainVC.view;
mainView.backgroundColor = COMMON_DARK;
var bgImgView = new ImgView();
bgImgView.imageUrl = "HomePageDefaultBg";
mainView.addSubView(bgImgView);
bgImgView.vrt_layout.topEqualToView(null).leftEqualToView(null).heightIs(235).widthRatioToView(null,1);

var avatarView = new ImgView();
avatarView.cornerRadius = 75.0/2.0 * kHeightScale;
bgImgView.addSubView(avatarView);
avatarView.vrt_layout.topSpaceToView(null,30).centerXEqualToView(null).heightIs(75).widthEqualToHeight();

var nicknameLabel = new displayLabelS1();
nicknameLabel.fontSize = 15 * kFontScale;
bgImgView.addSubView(nicknameLabel);
nicknameLabel.vrt_layout.topSpaceToView(avatarView,10).centerXEqualToView(null).heightIs(30).widthIs(200);

var ikuCodeLabel = new displayLabelS1();
bgImgView.addSubView(ikuCodeLabel);
ikuCodeLabel.vrt_layout.topSpaceToView(nicknameLabel,10).leftEqualToView(nicknameLabel).heightIs(25).widthIs(90);

var fansLabel = new displayLabelS1();
bgImgView.addSubView(fansLabel);
fansLabel.vrt_layout.topSpaceToView(nicknameLabel,10).leftSpaceToView(ikuCodeLabel,20).heightIs(25).widthIs(90);

var verifyLabel = new displayLabelS1();
bgImgView.addSubView(verifyLabel);
verifyLabel.vrt_layout.topSpaceToView(fansLabel,10).leftEqualToView(null).heightIs(25).widthRatioToView(bgImgView,1);

var listHeader = new displayLabelS1();
listHeader.text = "TA的动态";
listHeader.textColor = COMMON_YELLOW;
listHeader.fontSize = 16;
mainView.addSubView(listHeader);
listHeader.vrt_layout.topSpaceToView(bgImgView,5).leftEqualToView(null).heightIs(30).widthRatioToView(null,1);


var dynamicReq = new HttpRequest("dynamic/getDynamicByUserId",function(data,info){
    if(data != null && data != undefined && data.isSuccess == true)
    {
        if(data.jsonData != null)
        {
            _this.dynamicDataArray = data.jsonData;
        }
    }
});

var userInfoReq = new HttpRequest("user/getSomeUserInfoByUserId",function(data,info){
    if(data != null && data != undefined && data.isSuccess == true)
    {
        if(data.jsonData != null)
        {
            var userModel = data.jsonData;
            var fdStart = userModel.userAvatarUrl.indexOf("http");
            if(fdStart == 0){
                _this.avatarView.imageUrl = userModel.userAvatarUrl;
            }
            else if(fdStart == -1){
                _this.avatarView.imageUrl = _this.staticSourceBaseUrl + userModel.userAvatarUrl;
            }

            _this.nicknameLabel.text = userModel.userNickName;
            _this.ikuCodeLabel.text = userModel.userDcCode;
            if(userModel.userVerify != null && userModel.userVerify != undefined && userModel.userVerify.length > 0)
                _this.verifyLabel.text = "官方认证 : "+ userModel.userVerify;
            else
                _this.verifyLabel.text = "无认证";
        }
    }
});

var fansReq = new HttpRequest("watch/getFansByUserId",function(data,info){
    if(data != null && data != undefined && data.isSuccess == true)
    {
        if(data.jsonData != null)
        {
            _this.fansLabel.text = "粉丝 : " + data.jsonData.length;
        }
    }
});
//在视图不同阶段添加回调
mainVC.addCallBackViewDidLoad(function(){
});
//mainVC.addCallBackViewWillAppear(function(){
//dynamicReq.request_oniKu({"userId":getThisNavigation().getPushedParam().targetUserId});
//userInfoReq.request_oniKu({"userId":getThisNavigation().getPushedParam().targetUserId});
//fansReq.request_oniKu({"targetUserId":getThisNavigation().getPushedParam().targetUserId});
//});

mainVC.addCallBackViewDidAppear(function(){
  });
mainVC.addCallBackViewWillDisappear(function(){
      });
api_commitVC(mainVC);