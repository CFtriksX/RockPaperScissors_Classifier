using SkiaSharp;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Essentials;
using Xamarin.Forms;

namespace ImageInpaintingApp
{
    public partial class MainPage : ContentPage
    {
        public MainPage()
        {
            InitializeComponent();
        }

        private async void OnPickerClickedAsync(object sender, EventArgs e)
        {
            try
            {
                var result = await MediaPicker.PickPhotoAsync(new MediaPickerOptions
                {
                    Title = "Select an image"
                });

                var stream = await result.OpenReadAsync();
                SKBitmap bitmap = SKBitmap.Decode(stream);
                await Navigation.PushAsync(new PhotoCroppingPage(pickedImage, bitmap));
                pickedImage.Source = ImageSource.FromStream(() => stream);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
            }
        }

        private async void OnTakerClickedAsync(object sender, EventArgs e)
        {
            try
            {
                var result = await MediaPicker.CapturePhotoAsync(new MediaPickerOptions
                {
                    Title = "Take a picture"
                });

                var stream = await result.OpenReadAsync();
                SKBitmap bitmap = SKBitmap.Decode(stream);
                await Navigation.PushAsync(new PhotoCroppingPage(pickedImage, bitmap));
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
            }
        }

    }
}
