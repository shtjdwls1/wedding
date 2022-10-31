const webpack = require('webpack');
const path = require('path');
const context = path.join(__dirname, '');


module.exports = {
    entry: {
        onLoad: [
            path.join(context, '/js/module/common/config.js'),				// config값들 window에 붙이는 모듈
            path.join(context, '/js/module/common/customize.js'),			// js내장객체 확장및 변경 모듈
            path.join(context, '/js/bridge/bridgeFactory.js'),				// bridge
            path.join(context, '/js/module/common/socketReceive.js'),		// 소켓
            path.join(context, '/js/module/common/historyUtil.js')// HistoryUtil
        ],
        sample: path.join(context, '/js/sample/sample.js'),
        join: path.join(context, '/js/join/join.js'),
        login: path.join(context, '/js/wedding/login.js'),
        index: path.join(context, '/js/wedding/index.js'),
        backBtn: path.join(context, '/js/wedding/backBtn.js'),
        imgToBig: path.join(context, '/js/wedding/imgToBig.js'),
        mobiscroll: path.join(context, '/js/wedding/mobiscroll.jquery.min.js'),
        moveTopBtn: path.join(context, '/js/wedding/moveTopBtn.js'),
        plannerDetail: path.join(context, '/js/wedding/plannerDetail.js'),
        reviewBtn: path.join(context, '/js/wedding/reviewBtn.js'),
        reserveCancelBtn: path.join(context, '/js/wedding/reserveCancelBtn.js'),
        star: path.join(context, '/js/wedding/star.js'),
        textareaMax: path.join(context, '/js/wedding/textareaMax.js'),
        weddingDetail: path.join(context, '/js/wedding/weddingDetail.js'),
        changeCommonInfo: path.join(context, '/js/wedding/changeCommonInfo.js'),
        changeCommonInfoForm: path.join(context, '/js/wedding/changeCommonInfoForm.js'),
        wedInfoForm: path.join(context, '/js/wedding/photo/photoUpload.js'),
        wedImgSlide: path.join(context, '/js/wedding/WedImgSlide.js'),
        plannerReviewData: path.join(context, '/js/wedding/plannerReviewData.js')
        numberChangeKorean: path.join(context, '/js/wedding/numberChangeKorean.js'),
        counselCheckBtn: path.join(context, '/js/wedding/counselCheckBtn.js')
    },
    devtool: 'eval',//inline-source-map, eval
    devServer: {
        hot: true,
        historyApiFallback: true,
        compress: true,
        disableHostCheck: true,
        publicPath: "/resources/build/",
        host: "0.0.0.0",
        port: 80,
        clientLogLevel: 'silent',
        proxy: {
            "**": "http://localhost:8080/"
        }
    },
    optimization: {
        removeAvailableModules: false,
        removeEmptyChunks: false,
        splitChunks: false,
    },
    cache: true,
    resolve: {
        modules: [path.resolve('.'), '.', "node_modules"],
        extensions: [".js", ".json", ".ts"],
        alias: {
            /** import시 리소스 경로 잡아주는 알리아스 */
            '@': path.join(context, 'js'),
        }
    },
    output: {
        path: path.join(context, '/build/'),
        filename: '[name].bundle.js',
        publicPath: 'build',
        chunkFilename: "[name].bundle.js",
        libraryTarget: 'var',
        library: '[name]'
    },
    node: {
        global: true
    },
    mode: 'development',
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules)/,

                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: [
                            ["@babel/env", {
                                "targets": {
                                    "browsers": [">=1%", "ie >= 11", "android >= 4"]
                                }
                            }]
                        ],
                        plugins: ["dynamic-import-node", "@babel/plugin-transform-modules-commonjs", "transform-es2015-typeof-symbol", "@babel/plugin-transform-runtime"]
                    }
                }]
            },
            {
                test: /\.ts$/,
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: [
                            '@babel/preset-env',
                            '@babel/preset-typescript'
                        ]

                    }
                }]
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.exec\.js$/,
                use: ['script-loader']
            },
            {
                test: /\.(jpe?g|png|woff|woff2|eot|ttf|svg)(\?[a-z0-9=.]+)?$/,
                loader: 'file-loader'
            },
            {
                test: /(\.tpl|\.html)$/,
                loader: 'art-template-loader'
            }
        ]
    },
    plugins: [
        new webpack.DefinePlugin({
            /* Global 변수 */
            // DAUMKEY : JSON.stringify(daumKey),
            profile: 'local',
        })
    ],
    externals: {
        moment: 'moment',
        axios: 'axios',
        $: 'jQuery',
        jQuery: 'jQuery',
        jquery: 'jQuery',
        _: "_",
    }
};

if (process.env.NODE_ENV === 'production') {
    module.exports.mode = "production";
    module.exports.devtool = '';
    module.exports.plugins = [
        new webpack.DefinePlugin({
            /* Global 변수 */
            profile: 'real',
        })
    ]
} else if (process.env.NODE_ENV === 'development') {
    module.exports.mode = "development";
    module.exports.devtool = '';
    module.exports.plugins = [
        new webpack.DefinePlugin({
            profile: 'real',
        })
    ]
}
