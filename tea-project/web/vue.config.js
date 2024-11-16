module.exports = {
    chainWebpack: config =>{
        config.plugin('html')
            .tap(args => {
                args[0].title = "奶茶点餐系统";
                args[0].keywords = "奶茶点餐系统";
                args[0].description = "奶茶点餐系统";
                return args;
            })
    },
};
