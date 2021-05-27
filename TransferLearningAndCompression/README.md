# Rock Paper Scissors | Tensorflow Model training and compression
***
This Fodler contains the code that do transfer learning on a pre-trained model `TransferLearning.ipynb`, this repo will use the [tf2-preview/mobbilenet_v2 model](https://tfhub.dev/google/tf2-preview/mobilenet_v2/classification/4).

It also contains the code that compress the trained model to TensorFlow Lite model `TFliteCompression.ipynb`.

`metadata_writter.py` will add metadata to the TF Lite model, It allow android studio to have clean code with the model.

## Table of Contents
***
1. [Set Up env](#set-up-env)
2. [Dataset](#dataset)
3. [Transfer learning and Compression](#transfer-learning-and-compression)
4. [Metadata](#metadata)
## Set Up env
***
This project use **Python 3.8**, make sure you run it with the correct version!

Before going into the code you must install the virtual env with all its dependencies and run it:
### Windows
In a command prompt in this folder run those command:
```
$ python -m venv .venv
$ .\.venv\Scripts\activate
$ pip install --upgrade pip
$ pip install -r .\requirements.txt
```
**You may need to disable the max path lenght limit for python**
### Linux
In a terminal in this forlder run those command:
```
$ python -m venv .venv
$ source .venv/bin/activate
$ pip install --upgrade pip
$ pip install -r requirements.txt
```
## Dataset
***
The dataset is composed of 2892 images, 964 for each sign. You can access the dataset by clicking [here](https://laurencemoroney.com/datasets.html#rock-paper-scissors-dataset).

The website seems to be unstable and is often offline, you can download the [train set](https://storage.googleapis.com/laurencemoroney-blog.appspot.com/rps.zip) and the [test set](https://storage.googleapis.com/laurencemoroney-blog.appspot.com/rps-test-set.zip) here without using the homepage.

Once download you must put both set in their respective folder, you must respect this hierarchy otherwise you will have to change the code:
* Dataset
    - Test
        - paper
        - rock
        - scissors
    - Train
        - paper
        - rock
        - scissors
## Transfer learning and Compression
***
You first need to do the transfer learning part, launch the `TransferLearning` notebook, attach it to your virtual env and follow the instruction.

The training process can be long, you can modify the number of epochs if you want to go quick or if you want to optimize it.

You can then pass to the compression part, launch the `TFliteCompression` and follow the instruction.

## Metadata
***
TensorFlow Lite metadata provides a standard for model descriptions. The metadata is an important source of knowledge about what the model does and its input / output information. The metadata consists of both:
* Human readable parts which convey the best practice when using the model, and
* Machine readable parts that can be leveraged by code generators.

**The metadata is needed if you want to use your TF Lite model in the app.**
### Windows
In a command prompt, run this command:
```
$ .venv\Script\python .\metadata_writter.py \
    --model_file=.\saved_models\TFLiteModel\RPS_Fine_Tuned_Model.tflite \
    --label_file=.\saved_models\TFLiteModel\label.txt \
    --export_directory=saved_models\MetadataTFLModel
```
### Linux
In a terminal, run this command:
```
.venv/bin/python ./metadata_writter.py \
    --model_file=./saved_models/TFLiteModel/RPS_Fine_Tuned_Model.tflite \
    --label_file=./saved_models/TFLiteModel/label.txt \
    --export_directory=saved_models/MetadataTFLModel
```
