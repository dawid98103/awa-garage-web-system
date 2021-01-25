const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {

    // bundling mode
    mode: 'development',

    // entry files
    entry: {
        app: path.join(__dirname, 'src', 'index.tsx')
    },

    target: 'web',

    devtool: 'inline-source-map',

    // output bundles (location)
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: '[name].[contenthash].js',
    },

    // file resolutions
    resolve: {
        extensions: ['.ts', '.tsx', '.js'],
    },

    // loaders
    module: {
        rules: [
            {
                test: /\.tsx?/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
            {
                test: /\.css?/,
                use: [
                    'style-loader',
                    'css-loader'
                ]
            },
            {
                test: /\.(png|woff|woff2|eot|ttf|svg)$/,
                type: 'asset'
            }
        ]
    },

    plugins: [
        new HtmlWebpackPlugin({
            template: path.join(__dirname, 'src', 'index.html')
        })
    ],

    devServer: {
        contentBase: path.join(__dirname, 'public'),
        compress: true,
        publicPath: "/",
        hot: true,
        port: 5000
    },
};